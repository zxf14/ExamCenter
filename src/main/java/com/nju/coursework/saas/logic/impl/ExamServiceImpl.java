package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.*;
import com.nju.coursework.saas.data.entity.*;
import com.nju.coursework.saas.logic.service.ExamService;
import com.nju.coursework.saas.logic.service.GroupService;
import com.nju.coursework.saas.logic.service.MailService;
import com.nju.coursework.saas.logic.vo.*;
import com.nju.coursework.saas.web.response.GeneralResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
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
                .filter(s -> studentRepository.findByEmail(s.split(" ")[1]) != null &&
                studentRepository.findByEmail(s.split(" ")[1]).size() > 0)
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
        GeneralResponse generalResponse = saveExam(userId, quizCount, testees, examConfigVO.getScores(),
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

    private GeneralResponse saveExam(int userId, int questionNum, List<Testee> testees, int score,
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
            List<Question> questions = questionRepository.findByCourseId(courseId);
            if (questionNum > questions.size()) {
                return new GeneralResponse(false, "设置的题目数量超过当前的题库题目数");
            }
            testees.forEach(
                    t -> {
                        t.setExamByExamId(exam);
                        testeeRepository.saveAndFlush(t);
                        Collections.shuffle(questions);
                        for(int i = 0;i < questionNum;i++) {
                            Quiz quiz = new Quiz();
                            quiz.setValue(score);
                            quiz.setQuestionByQuestionId(questions.get(i));
                            quiz.setTesteeByTesteeId(t);
                            quizs.add(quiz);
                        }
                    }
            );
            quizs.forEach(
                    q -> {
                        q.setExamByExamId(exam);
                        quizRepository.saveAndFlush(q);
                    });

        } catch (Exception e) {
            return new GeneralResponse(false, e.getMessage());
        }
        testees.forEach(t -> mailService.examKeyMail(t.getStudentMail(), t.getExamPassword(),
                t.getExamByExamId().getExamTitle()));
        return new GeneralResponse(true, "成功创建试卷");
    }

    private int random(int size) {
        return (int)Math.random() * size;
    }

    @Override
    public GeneralResponse submitExam(int testeeId, @RequestBody List<QuizVO> quizVOs) {

        List<Answer> answerList = new ArrayList<>();
        quizVOs.stream().forEach(
                quizVO -> {
                    Quiz quiz = quizRepository.findOne(quizVO.getId());
//                    Question question = quiz.getQuestionByQuestionId();
                    Question question = questionRepository.findOne(quizVO.getQuestion().getId());
                    //学生选择答案
                    List<Aoption> options = quizVO.getOptionId().stream().filter(optionId -> optionId != null).map(
                            optionId -> {
                                    Aoption option = optionRepository.findOne(Integer.parseInt(optionId));
                                    return option;
                            }
                    ).collect(Collectors.toList());
                    Answer answer = new Answer();
                    answer.setQuizByQuizId(quiz);
                    answer.setContent(String.join(" ", quizVO.getOptionId().stream().filter(optionId -> optionId != null).collect(Collectors.toList())));
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
            final int[] testeeScore = {0};
            answerList.forEach(a -> {
                a.setStudentByStudentId(testee.getStudentByStudentId());
                answerRepository.saveAndFlush(a);
                testeeScore[0] += a.getScore();
            });
//            testee.setScore(answerList.stream().mapToInt(Answer::getScore).sum());
            testee.setScore(testeeScore[0]);
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

    private void handleMulti(Question question, Quiz quiz, List<Aoption> aoptionList, Answer answer) {
        if (aoptionList == null) {
            answer.setScore(0);
            return;
        }
        List<Aoption> rightAoption = optionRepository.findByQuestion(question.getId()).stream()
                .filter(Aoption::getIsRight)
                .collect(Collectors.toList());
        int rightNum = 0;
        for (Aoption option : aoptionList) {
            if (option == null) {
                continue;
            }
            if (!option.getIsRight()) {
                answer.setScore(0);
                return;
            }
            rightNum ++;
        }
        if (rightNum == rightAoption.size()) {
            answer.setScore(quiz.getValue());
        } else {
            answer.setScore(quiz.getValue() / 2);
        }
    }

    @Override
    public ExamVO createExamBefore(Integer examId, Integer testeeId) {
        Exam exam = examRepository.findOne(examId);
        Testee testee = testeeRepository.findOne(testeeId);
        StudentVO studentVO = new StudentVO();
        studentVO.setStudentNo(testee.getStudentByStudentId().getStudentNo());
        studentVO.setMail(testee.getStudentMail());
        studentVO.setName(testee.getStudentName());
        List<QuestionVO> questions = quizRepository.findByTesteeId(testeeId).stream()
                .map(quiz -> {
                            Question question = quiz.getQuestionByQuestionId();
                            Collections.shuffle(optionRepository.findByQuestion(question.getId()));
                            questionRepository.saveAndFlush(question);
                            return new QuestionVO(question);
                        }
                ).collect(Collectors.toList());

        List<Value> value = quizRepository.findByTesteeId(examId).stream()
                .map(quiz -> new Value(quiz.getQuestionByQuestionId().getId(), quiz.getValue())).collect(Collectors.toList());
        List<Integer> filterValue = new ArrayList<>();
        for(int i = 0;i < questions.size();i++) {
            QuestionVO q = questions.get(i);
            value.stream().forEach(v -> {
                if (v.getQuestionId() == q.getId()) {
                    filterValue.add(new Integer(v.getValue()));
                }
            });
        }
        return new ExamVO(exam, questions, filterValue, studentVO);
    }

    @Override
    public ExamVO getExamAfter(int examId, String studentId) {
        Exam exam = examRepository.findOne(examId);
        Student student = null;
        StudentVO studentVO = null;
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

        List<Value> value = quizRepository.findByExamId(examId).stream()
                .map(quiz -> new Value(quiz.getQuestionByQuestionId().getId(), quiz.getValue())).collect(Collectors.toList());

        if (student != null) {
            answers = answerRepository.findByStudentId(studentId).stream()
                    .filter(answer -> answer.getQuizByQuizId().getExamByExamId().getId() == examId)
                    .map(answer -> new AnswerVO(answer))
                    .collect(Collectors.toList());

            studentVO = new StudentVO();
            studentVO.setName(student.getName());
            studentVO.setMail(student.getMail());
            studentVO.setStudentNo(student.getStudentNo());

        }
        List<AnswerVO> filterAnswer = new ArrayList<>();
        List<Integer> filterValue = new ArrayList<>();
        for(int i = 0;i < questions.size();i++) {
            QuestionVO q = questions.get(i);
            answers.stream().forEach(a -> {
                if (a.getQuestionId() == q.getId()) {
                    filterAnswer.add(a);
                }
            });
            value.stream().forEach(v -> {
                if (v.getQuestionId() == q.getId()) {
                    filterValue.add(new Integer(v.getValue()));
                }
            });
        }
        return new ExamVO(exam, questions, filterValue, filterAnswer, studentVO);
    }

    @Override
    public List<ExamVO> getExamAfterList(int examId) {
        List<Testee> testees = testeeRepository.findByExamId(examId);
        return testees.stream()
                .filter(t -> t.getStudentByStudentId() != null)
                .map(t -> getExamAfter(examId, t.getStudentByStudentId().getStudentNo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamVO> getExamInfoByCourse(int courseId) {
        List<Exam> examList = examRepository.findByCourseId(courseId);
        return examList.stream().map(e -> {
            List<QuizVO> questions = getQuestions(quizRepository.findByExamId(e.getId()));
            return new ExamVO(e, questions);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ExamVO> getExamInfoByStudent(String studentNo) {
        List<Testee> testeeList = testeeRepository.findByStudentId(studentNo);

        List<ExamVO> examList = testeeList.stream()
                .map(t -> {
                    List<QuizVO> questions = getQuestions(quizRepository.findByTesteeId(t.getId()));
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

    private List<QuizVO> getQuestions(List<Quiz> quizzes) {
        return quizzes.stream().map(i -> {
            Question question = questionRepository.findOne(i.getQuestionByQuestionId().getId());
            List<Aoption> optionVO = optionRepository.findByQuestion(question.getId());
            question.setAoptionsById(optionVO);
            QuizVO quizVO = new QuizVO(i, question);
            return quizVO;
        }).collect(Collectors.toList());
    }
}
@Data
@AllArgsConstructor
class Value {
    private int questionId;
    private int value;
}
