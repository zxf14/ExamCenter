package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guhan on 17/12/7.
 */
@Controller
@RequestMapping(value = "/quiz")
public class QuizController {
    @Autowired
    QuestionService questionService;    /*提供题库*/


}
