package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.StudentRepository;
import com.nju.coursework.saas.data.entity.Student;
import com.nju.coursework.saas.logic.service.MailService;
import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.loops.FillRect;

import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.substring;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MailService mailService;

    @Override
    public GeneralResponse register(StudentVO studentVO) {
        List<Student> students = studentRepository.findByNo(studentVO.getStudentNo());
        if (students.size() > 0) return new GeneralResponse(false, "学号已注册");
        Student student = new Student();
        student.setMail(studentVO.getMail());
        student.setName(studentVO.getName());
        student.setPassword(studentVO.getPassword());
        student.setStudentNo(studentVO.getStudentNo());

        studentRepository.saveAndFlush(student);
        GeneralResponse generalResponse = new GeneralResponse(true, "mail sent");
        return generalResponse;
    }

    @Override
    public GeneralResponse login(String studentNo, String password) {
        List<Student> students = studentRepository.findByNo(studentNo);
        if (students.size() == 0)
            return new GeneralResponse(false, "学号未注册");
        if (students.get(0).getPassword().equals(password))
            return new GeneralResponse(true, "");
        else
            return new GeneralResponse(false, "密码错误");
    }

    @Override
    public GeneralResponse getVerifyCode(String mail) {
        String verityCode = mail.hashCode() + new Date().hashCode() + "".substring(2, 8);
        mailService.validateMail(mail, verityCode);
        return new GeneralResponse(true, verityCode);
    }
}
