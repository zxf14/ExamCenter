package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guhan on 17/11/1.
 */
@Controller
@RequestMapping(value = "/v1/api")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        testService.test();
        return "TestCenterApplication --version 1";
    }
}
