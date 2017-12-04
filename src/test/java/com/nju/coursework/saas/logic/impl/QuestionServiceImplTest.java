package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.BaseTests;
import com.nju.coursework.saas.logic.service.QuestionService;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
public class QuestionServiceImplTest extends BaseTests {
    @Resource
    QuestionService questionService;

    @Test
    public void importQuestion() throws Exception {

        InputStream inputStream = new ClassPathResource("static/questionsList.xlsx").getInputStream();
        questionService.importQuestion(inputStream, 3);
    }

}