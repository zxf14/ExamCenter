package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.QuestionService;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by guhan on 17/11/8.
 */
@Controller
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/import")
    @ResponseBody
    public String importQuestion(@RequestParam("file") MultipartFile file, int courseId) throws IOException {
        GeneralResponse resp = questionService.importQuestion(file.getInputStream(), courseId);
        return JsonUtil.toJsonString(resp);
    }

    @GetMapping("/list")
    @ResponseBody
    public String getQuestion(int courseId) throws IOException {
        List<QuestionVO> list = questionService.getQuestions(courseId);
        GeneralResponse response = new GeneralResponse(true, "");
        response.putDate("questions", list);
        return JsonUtil.toJsonString(response);
    }
}
