package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.*;
import com.nju.coursework.saas.data.entity.*;
import com.nju.coursework.saas.logic.service.ExamService;
import com.nju.coursework.saas.logic.service.GroupService;
import com.nju.coursework.saas.logic.service.MailService;
import com.nju.coursework.saas.logic.vo.*;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExamServiceImpl implements ExamService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    TesteeRepository testeeRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    GroupRepository groupRepository;
    @Resource
    GroupService groupService;
    @Resource
    MailService mailService;

    @Override
    public GeneralResponse examConfig(int userId, int quizCount, List<Integer> scores, int groupId,
                                      List<QuestionVO> questions, String startTime, String endTime,
                                      String title, String place) {
        if (quizCount != questions.size()) {
            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
        }
        Instant timeStart = Timestamp.valueOf(startTime).toInstant();
        Instant timeEnd = Timestamp.valueOf(endTime).toInstant();
        if (timeStart.compareTo(timeEnd) > 0) {
            return new GeneralResponse(false, "考试开始时间晚于考试结束时间");
        }
        List<Testee> testees = new ArrayList<>();

        Groups group = groupRepository.findOne(groupId);
        List<String> studentName = Arrays.asList(group.getStudents().split(";"));
        studentName.stream().forEach(s -> {
            List<Student> students = studentRepository.findByName(s.split(" ")[0])
                    .stream().filter(student -> student.getMail() != null).collect(Collectors.toList());
            students.forEach(
                    stu -> {
                        Testee testee = new Testee();
                        testee.setStudentByStudentId(stu);
                        testee.setStudentMail(stu.getMail());
                        testees.add(testee);
                    });
        });

        return saveExam(userId, testees, scores, questions, startTime, endTime, title, place);
    }

    @Override
    public GeneralResponse examConfigByExcel(int userId, int quizCount, InputStream excel, String groupName,
                                             List<Integer> scores, List<QuestionVO> questions,
                                             String startTime, String endTime,
                                             String title, String place) {
        if (quizCount != scores.size()) {
            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
        }
        Instant timeStart = Timestamp.valueOf(startTime).toInstant();
        Instant timeEnd = Timestamp.valueOf(endTime).toInstant();
        if (timeStart.compareTo(timeEnd) > 0) {
            return new GeneralResponse(false, "考试开始时间晚于考试结束时间");
        }
        if (excel != null) {
            groupService.createGroup(userId, excel, groupName);
        } else {
            return new GeneralResponse(false, "无excel文件");
        }
        List<GroupsVO> validGroups = groupService.getGroups(userId).stream()
                .filter(g -> g.getName() == groupName).collect(Collectors.toList());

        List<Testee> testees = new ArrayList<>();
        validGroups.forEach(g -> {
            g.getStudents().stream().forEach(
                    s -> {
                        List<Student> students = studentRepository.findByName(s.split(" ")[0])
                                .stream().filter(student -> student.getMail() != null).collect(Collectors.toList());
                        students.forEach(
                                stu -> {
                                    Testee testee = new Testee();
                                    testee.setStudentByStudentId(stu);
                                    testee.setStudentMail(stu.getMail());
                                    testees.add(testee);
                                });
                    });
        });

        return saveExam(userId, testees, scores, questions, startTime, endTime, title, place);
    }

    private GeneralResponse saveExam(int userId, List<Testee> testees, List<Integer> scores, List<QuestionVO> questions,
                                     String startTime, String endTime, String title, String place) {

        List<Quiz> quizs = new ArrayList<>();
        User teacher = userRepository.findOne(userId);
        try {
            Exam exam = new Exam();
            exam.setUserByUserId(teacher);
            exam.setStartTime(startTime);
            exam.setEndTime(endTime);
            exam.setExamTitle(title);
            exam.setExamPlace(place);
            examRepository.saveAndFlush(exam);
            IntStream.range(0, questions.size()).forEach(
                    i -> {
                        Quiz quiz = new Quiz();
                        quiz.setValue(scores.get(i));
                        quiz.setQuestionByQuestionId(questionRepository.findOne(questions.get(i).getId()));
                        quizs.add(quiz);
                    }
            );
            quizs.forEach(
                    q -> {
                        q.setExamByExamId(exam);
                        quizRepository.saveAndFlush(q);
                    });
            testees.forEach(
                    t -> {
                        t.setExamByExamId(exam);
                        testeeRepository.saveAndFlush(t);
                    }
            );

        } catch (Exception e) {
            return new GeneralResponse(false, e.getMessage());
        }
        return new GeneralResponse(true, "成功创建试卷");
    }

    @Override
    public GeneralResponse submitExam(int testeeId, List<QuizVO> quizVO) {

        List<Answer> answerList = new ArrayList<>();
        quizVO.stream().forEach(
                q -> {
                    Quiz quiz = quizRepository.findOne(q.getId());
                    Question question = quiz.getQuestionByQuestionId();
                    List<Aoption> options = q.getOptionId().stream().map(
                            i -> {
                                Aoption option = optionRepository.findOne(Integer.parseInt(i));
                                return option;
                            }
                    ).collect(Collectors.toList());
                    Answer answer = new Answer();
                    answer.setQuizByQuizId(quiz);
                    answer.setContent(String.join(";", q.getOptionId()));
                    if (question.getType() == 0) {
                        handleSingle(quiz, options, answer);
                    } else {
                        handleMulti(question, quiz, options, answer);
                    }
                    answerList.add(answer);

                }
        );
        try {
            Testee testee = testeeRepository.findOne(testeeId);
            testee.setScore(answerList.stream().mapToInt(Answer::getScore).sum());
            answerList.forEach(a -> {
                a.setStudentByStudentId(testee.getStudentByStudentId());
                answerRepository.saveAndFlush(a);
            });
            testeeRepository.saveAndFlush(testee);
        } catch (Exception e) {
            return new GeneralResponse(false, e.getMessage());
        }
        //向考生发送考试结果
        Testee testee = testeeRepository.findOne(testeeId);
        mailService.scoreMail(testee.getStudentMail(), testee.getExamByExamId().getExamTitle(), testee.getScore());
        return new GeneralResponse(true, "试卷提交成功");
    }

    private void handleSingle(Quiz quiz, List<Aoption> aoption, Answer answer) {
        if (aoption.size() == 1 && aoption.get(0).getIsRight()) {
            answer.setScore(quiz.getValue());
        } else {
            answer.setScore(0);
        }
    }

    private void handleMulti(Question question, Quiz quiz, List<Aoption> aoption, Answer answer) {
        List<Aoption> rightAoption = question.getAoptionsById().stream().filter(Aoption::getIsRight)
                .collect(Collectors.toList());
        for (Aoption option : aoption) {
            if (!option.getIsRight()) {
                answer.setScore(0);
                return;
            }
        }
        List<Aoption> filterAoption = aoption.stream().filter(Aoption::getIsRight).collect(Collectors.toList());
        if (filterAoption.size() == rightAoption.size()) {
            answer.setScore(quiz.getValue());
        } else {
            answer.setScore(quiz.getValue() / 2);
        }
    }

    @Override
    public List<ExamVO> createExamBefore(List<Integer> examId) {
        return examId.stream().map(id ->
                getExamAfter(id, null)).collect(Collectors.toList());
    }

    @Override
    public ExamVO getExamAfter(int examId, String studentId) {
        Exam exam = examRepository.findOne(examId);
        Student student = studentRepository.findByNo(studentId).get(0);
        List<AnswerVO> answers = new ArrayList<>();
        List<QuestionVO> questions = quizRepository.findByExamId(examId).stream()
                .map(quiz -> {
                            Question question = quiz.getQuestionByQuestionId();
                            Collections.shuffle(optionRepository.findByQuestion(question.getId()));
                            questionRepository.saveAndFlush(question);
                            return new QuestionVO(question);
                        }
                ).collect(Collectors.toList());

        List<Integer> value = quizRepository.findByExamId(examId).stream()
                .map(quiz -> new Integer(quiz.getValue())).collect(Collectors.toList());

        if (student != null) {
            answers = answerRepository.findByStudentId(studentId).stream()
                    .map(answer -> new AnswerVO(answer)).collect(Collectors.toList());
        }
        return new ExamVO(exam, questions, value, answers);
    }

    @Override
    public List<ExamVO> getExamAfterList(int examId) {
        List<Testee> testees = testeeRepository.findByExamId(examId);
        return testees.stream()
                .map(t -> getExamAfter(examId, t.getStudentByStudentId().getStudentNo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamVO> getExamInfoByCourse(int courseId) {
        List<Exam> examList = examRepository.findByCourseId(courseId);
        return examList.stream().map(e -> new ExamVO(e)).collect(Collectors.toList());
    }

    @Override
    public List<ExamVO> getExamInfoByStudent(String studentNo) {
        List<Testee> testeeList = testeeRepository.findByStudentId(studentNo);
        List<ExamVO> examList = testeeList.stream()
                .map(t -> new ExamVO(t.getExamByExamId())).collect(Collectors.toList());
        return examList;
    }

}