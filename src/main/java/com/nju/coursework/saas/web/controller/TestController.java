package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.TestService;
import com.nju.coursework.saas.web.annotation.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guhan on 17/11/1.
 */
@Controller
@RequestMapping(value = {"/teacher/**", "/student/**","/login/**", "/"})
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
}
