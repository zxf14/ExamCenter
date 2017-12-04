package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.ExamRepository;
import com.nju.coursework.saas.data.db.StudentRepository;
import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.*;
import com.nju.coursework.saas.logic.service.ExamConfigService;
import com.nju.coursework.saas.logic.service.GroupService;
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

@Service
public class ExamConfigServiceImpl implements ExamConfigService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamRepository examRepository;

    @Resource
    GroupService groupService;

    @Override
    public GeneralResponse examConfig(int userId, int quizCount, List<QuizVO> quizVO, List<StudentVO> studentVO,
                                      Timestamp startTime, Timestamp endTime) {
        if (quizCount != quizVO.size()) {
            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
        }
        User teacher = userRepository.findOne(userId);

        List<Testee> testees = new ArrayList<>();
        List<Quiz> quizs = new ArrayList<>();

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

        quizVO.stream().forEach(
                q -> {
                    Quiz quiz = new Quiz();
                    //TODO
//                    quiz.setValue(q.getValue());
                    quizs.add(quiz);
                }
        );
        Exam exam = new Exam();
        exam.setUserByUserId(teacher);
        exam.setQuizzesById(quizs);

        //TODO
//        exam.setQuizCount(quizCount);
        exam.setStartTime(startTime);
        exam.setEndTime(endTime);
        exam.setTesteesById(testees);

        examRepository.saveAndFlush(exam);
        return new GeneralResponse(true, "成功创建试卷");
    }

    @Override
    public GeneralResponse examConfigByExcel(int userId, int quizCount, InputStream excel, String groupName,
                                             List<QuizVO> quizVO, Timestamp startTime, Timestamp endTime) {
        if (quizCount != quizVO.size()) {
            return new GeneralResponse(false, "设置的试题总数与实际题数量不匹配");
        }
        if (excel != null) {
            groupService.createGroup(userId, excel, groupName);
        }
        List<Groups> validGroups = groupService.getGroups(userId).stream()
                .filter(g -> g.getName() == groupName).collect(Collectors.toList());
        //TODO 从groups中获取name和mail
        List<Quiz> quizs = new ArrayList<>();
        quizVO.stream().forEach(
                q -> {
                    Quiz quiz = new Quiz();
                    //TODO
//                    quiz.setValue(q.getValue());
                    quizs.add(quiz);
                }
        );
        User teacher = userRepository.findOne(userId);
        Exam exam = new Exam();
        exam.setUserByUserId(teacher);
        exam.setQuizzesById(quizs);
        //TODO
//        exam.setQuizCount(quizCount);
        exam.setStartTime(startTime);
        exam.setEndTime(endTime);

        examRepository.saveAndFlush(exam);
        return new GeneralResponse(true, "成功创建试卷");
    }

}
