package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.UserService;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guhan on 17/11/8.
 */
@Controller
@RequestMapping(value = "/test/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    @ResponseBody
    public GeneralResponse login(String email, String password) {
        return userService.login(email, password);
    }
}
