package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.QuestionService;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by guhan on 17/11/8.
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/question/import")
    @ResponseBody
    public String importQuestion(@RequestParam("file")MultipartFile file, @RequestParam("courseId") int courseId) throws IOException {
        GeneralResponse resp = questionService.importQuestion(file.getInputStream(), courseId);
        String js = JsonUtil.toJsonString(resp);
        return js;
    }

}
