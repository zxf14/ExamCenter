package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.*;
import com.nju.coursework.saas.data.entity.*;
import com.nju.coursework.saas.logic.service.ExamConfigService;
import com.nju.coursework.saas.logic.service.GroupService;
import com.nju.coursework.saas.logic.vo.GroupsVO;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExamConfigServiceImpl implements ExamConfigService {

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

    @Resource
    GroupService groupService;

    @Override
    public GeneralResponse examConfig(int userId, int quizCount, List<Integer> scores, List<StudentVO> studentVO,
                                      List<QuestionVO> questions, Timestamp startTime, Timestamp endTime) {
        if (quizCount != questions.size()) {
            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
        }
        List<Testee> testees = new ArrayList<>();

        studentVO.stream().filter(s -> s.getMail() != null).forEach(
                s -> {
                    List<Student> student = studentRepository.findByNo(s.getStudentNo());
                    String mail = s.getMail();
                    Testee testee = new Testee();
                    testee.setStudentMail(mail);
                    testee.setStudentByStudentId(student.get(0));
                    testees.add(testee);
                }
        );

        return saveExam(userId, testees, scores, questions, startTime, endTime);
    }

    @Override
    public GeneralResponse examConfigByExcel(int userId, int quizCount, InputStream excel, String groupName,
                                             List<Integer> scores, List<QuestionVO> questions,
                                             Timestamp startTime, Timestamp endTime) {
        if (quizCount != scores.size()) {
            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
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

        return saveExam(userId, testees, scores, questions, startTime, endTime);
    }

    private GeneralResponse saveExam(int userId, List<Testee> testees, List<Integer> scores, List<QuestionVO> questions,
                                     Timestamp startTime, Timestamp endTime) {

        List<Quiz> quizs = new ArrayList<>();
        User teacher = userRepository.findOne(userId);
        try {
            Exam exam = new Exam();
            exam.setUserByUserId(teacher);
            exam.setStartTime(startTime);
            exam.setEndTime(endTime);
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


}
