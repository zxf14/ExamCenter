package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.BaseTests;
import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.logic.vo.StudentVO;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
public class StudentServiceImplTest extends BaseTests {
    @Resource
    StudentService studentService;

    @Test
    public void login() throws Exception {
        System.out.println(studentService.login("aa", "a"));
    }

    @Test
    public void register() throws Exception {
        System.out.println(studentService.register(new StudentVO("myname", "14125", "password", "mial@11")));
    }

    @Test
    public void getVerifyCode() {
        System.out.println(studentService.getVerifyCode("mail1@qq.com"));
    }
}