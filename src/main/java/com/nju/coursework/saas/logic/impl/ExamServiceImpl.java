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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
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
    public GeneralResponse examConfig(int userId, ExamConfigVO examConfigVO) {
        int quizCount = examConfigVO.getQuestionNum();
        if (quizCount != examConfigVO.getQuestions().size()) {
            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
        }
        try {
            Instant timeStart = Timestamp.valueOf(examConfigVO.getStartTime()).toInstant();
            Instant timeEnd = Timestamp.valueOf(examConfigVO.getEndTime()).toInstant();
            if (timeStart.compareTo(timeEnd) > 0) {
                return new GeneralResponse(false, "考试开始时间晚于考试结束时间");
            }
        } catch (Exception e) {
            return new GeneralResponse(false, "时间格式错误");
        }
        List<Testee> testees = new ArrayList<>();

        Groups group = groupRepository.findOne(examConfigVO.getGroupId());
        List<String> studentName = Arrays.asList(group.getStudents().split(";")).stream()
                .filter(s -> s.split(" ").length == 2)
                .collect(Collectors.toList());

        studentName.stream().forEach(s -> {
            List<Student> students = studentRepository.findByEmail(s.split(" ")[1]);
            Testee testee = new Testee();
            testee.setStudentMail(s.split(" ")[1]);
            testee.setStudentName(s.split(" ")[0]);
            testee.setState(0);
            String password = (testee.hashCode() + Instant.now().hashCode() + "").substring(2, 8);
            testee.setExamPassword(password);
            if (students != null && students.size() > 0) {
                testee.setStudentByStudentId(students.get(0));
            }
            testees.add(testee);
        });
        GeneralResponse generalResponse = saveExam(userId, testees, examConfigVO.getScores(), examConfigVO.getQuestions(),
                examConfigVO.getStartTime(), examConfigVO.getEndTime(),
                examConfigVO.getTitle(), examConfigVO.getPlace(), examConfigVO.getCourseId());

        return generalResponse;
    }

    @Override
    public GeneralResponse examConfigByExcel(int userId, ExamConfigVO examConfigVO, InputStream excel) {
//        if (examConfigVO.getQuestionNum() != examConfigVO.getQuestions().size()) {
//            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
//        }
//        try {
//            Instant timeStart = Timestamp.valueOf(examConfigVO.getStartTime()).toInstant();
//            Instant timeEnd = Timestamp.valueOf(examConfigVO.getEndTime()).toInstant();
//            if (timeStart.compareTo(timeEnd) > 0) {
//                return new GeneralResponse(false, "考试开始时间晚于考试结束时间");
//            }
//        } catch (Exception e) {
//            return new GeneralResponse(false, "时间格式错误");
//        }
//        if (excel != null) {
//            groupService.createGroup(userId, excel, examConfigVO.getGroupName());
//        } else {
//            return new GeneralResponse(false, "无excel文件");
//        }
//        List<GroupsVO> validGroups = groupService.getGroups(userId).stream()
//                .filter(g -> g.getName() == examConfigVO.getGroupName()).collect(Collectors.toList());
//
//        List<Testee> testees = new ArrayList<>();
//        validGroups.forEach(g -> {
//            g.getStudents().stream().forEach(
//                    s -> {
//                        List<Student> students = studentRepository.findByName(s.split(" ")[0])
//                                .stream().filter(student -> student.getMail() != null).collect(Collectors.toList());
//                        students.forEach(
//                                stu -> {
//                                    Testee testee = new Testee();
//                                    testee.setStudentByStudentId(stu);
//                                    testee.setStudentMail(stu.getMail());
//                                    String password = (testee.hashCode() + Instant.now().hashCode() + "").substring(2,8);
//                                    testee.setExamPassword(password);
//                                    testees.add(testee);
//                                });
//                    });
//        });
//
//        return saveExam(userId, testees, examConfigVO.getScores(), examConfigVO.getQuestions(),
//                examConfigVO.getStartTime(), examConfigVO.getEndTime(),
//                examConfigVO.getTitle(), examConfigVO.getPlace(), examConfigVO.getCourseId());
        return null;
    }

    private GeneralResponse saveExam(int userId, List<Testee> testees, List<Integer> scores, List<QuestionVO> questions,
                                     String startTime, String endTime, String title, String place, int courseId) {

        List<Quiz> quizs = new ArrayList<>();
        User teacher = userRepository.findOne(userId);
        Course course = courseRepository.findOne(courseId);
        try {
            Exam exam = new Exam();
            exam.setCourseById(course);
            exam.setUserByUserId(teacher);
            exam.setStartTime(startTime);
            exam.setEndTime(endTime);
            exam.setExamTitle(title);
            exam.setExamPlace(place);
            examRepository.saveAndFlush(exam);
            if (questions.size() > 0) {
                IntStream.range(0, questions.size()).forEach(
                        i -> {
                            Quiz quiz = new Quiz();
                            quiz.setValue(scores.get(i));
                            quiz.setQuestionByQuestionId(questionRepository.findOne(questions.get(i).getId()));
                            quizs.add(quiz);
                        }
                );
            }
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
        testees.forEach(t -> mailService.examKeyMail(t.getStudentMail(), t.getExamPassword(),
                t.getExamByExamId().getExamTitle()));
        return new GeneralResponse(true, "成功创建试卷");
    }

    @Override
    public GeneralResponse submitExam(int testeeId, @RequestBody List<QuizVO> quizVO) {

        List<Answer> answerList = new ArrayList<>();
        quizVO.stream().forEach(
                q -> {
                    Quiz quiz = quizRepository.findOne(q.getId());
//                    Question question = quiz.getQuestionByQuestionId();
                    Question question = questionRepository.findOne(q.getQuestion().getId());
                    List<Aoption> options = q.getOptionId().stream().map(
                            i -> {
                                if (i != null) {
                                    Aoption option = optionRepository.findOne(Integer.parseInt(i));
                                    return option;
                                }
                                return null;
                            }
                    ).collect(Collectors.toList());
                    Answer answer = new Answer();
                    answer.setQuizByQuizId(quiz);
                    answer.setContent(String.join(" ", q.getOptionId()));
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
            Instant endTime = Timestamp.valueOf(testee.getExamByExamId().getEndTime()).toInstant();
            if (endTime.compareTo(Instant.now()) > 0) {
                testee.setState(3);
            } else {
                testee.setState(2);
            }
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
        if (aoption != null && aoption.size() == 1 && aoption.get(0).getIsRight()) {
            answer.setScore(quiz.getValue());
        } else {
            answer.setScore(0);
        }
    }

    private void handleMulti(Question question, Quiz quiz, List<Aoption> aoption, Answer answer) {
        List<Aoption> rightAoption = optionRepository.findByQuestion(question.getId()).stream().filter(Aoption::getIsRight)
                .collect(Collectors.toList());
        for (Aoption option : aoption) {
            if (option == null || !option.getIsRight()) {
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
    public ExamVO createExamBefore(Integer examId) {

        return getExamAfter(examId, null);
    }

    @Override
    public ExamVO getExamAfter(int examId, String studentId) {
        Exam exam = examRepository.findOne(examId);
        Student student = null;

        if (studentId != null) {
            List<Student> students = studentRepository.findByNo(studentId);
            if (students.size() > 0) {
                student = students.get(0);
            }
        }

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
        return examList.stream().map(e -> {
            List<QuizVO> questions = getQuestions(e.getId());
            return new ExamVO(e, questions);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ExamVO> getExamInfoByStudent(String studentNo) {
        List<Testee> testeeList = testeeRepository.findByStudentId(studentNo);

        List<ExamVO> examList = testeeList.stream()
                .map(t -> {
                    List<QuizVO> questions = getQuestions(t.getExamByExamId().getId());
                    ExamVO examVO = new ExamVO(t.getExamByExamId(), questions, t.getId(), t.getState());
                    return examVO;
                }).collect(Collectors.toList());
        return examList;
    }

    @Override
    public boolean attendExam(int testeeId, String password) {
        Testee testee = testeeRepository.findOne(testeeId);
        return testee.getExamPassword().equals(password);
    }

    private List<QuizVO> getQuestions(int examByExamId) {
        List<Quiz> quizzes = quizRepository.findByExamId(examByExamId);
        return quizzes.stream().map(i -> {
            Question question = questionRepository.findOne(i.getQuestionByQuestionId().getId());
            List<Aoption> optionVO = optionRepository.findByQuestion(question.getId());
            question.setAoptionsById(optionVO);
            QuizVO quizVO = new QuizVO(i, question);
            return quizVO;
        }).collect(Collectors.toList());
    }
}
