package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.StudentRepository;
import com.nju.coursework.saas.data.entity.Student;
import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

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
        return new GeneralResponse(true, "");
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
    public String getVerifyCode(String mail) {
        return (mail.hashCode() + new Date().hashCode() + "").substring(2, 8);
    }
}
