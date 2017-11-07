package com.nju.coursework.saas.service;

import com.nju.coursework.saas.db.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by guhan on 17/11/1.
 */
@Service
public class TestService {
    @Autowired
    StudentRepository studentRepository;

    public void test(){
        System.out.println("test");
        System.out.println(studentRepository.findAll().toString());
    }
}
