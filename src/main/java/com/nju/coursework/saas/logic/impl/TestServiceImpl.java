package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.StudentRepository;
import com.nju.coursework.saas.logic.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TestServiceImpl implements TestService {
    @Autowired
    StudentRepository studentRepository;

    public void test() {
        System.out.println("test");
        System.out.println(studentRepository.findAll().toString());
    }
}
