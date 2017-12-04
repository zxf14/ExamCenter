package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.BaseTests;
import com.nju.coursework.saas.logic.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
public class UserServiceImplTest extends BaseTests {
    @Resource
    UserService userService;

    @Test
    public void login() throws Exception {
        System.out.println(userService.login("aa", "a"));
    }

}