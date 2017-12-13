package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.CourseService;
import com.nju.coursework.saas.logic.vo.CourseVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/test/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/create")
    @ResponseBody
    public String createCourse(String courseName, HttpSession session) throws IOException {
        GeneralResponse resp = courseService.createCourse((Integer) session.getAttribute("id"), courseName);
        return JsonUtil.toJsonString(resp);
    }

    @GetMapping("/list")
    @ResponseBody
    public String getCourse(HttpSession session) throws IOException {
        List<CourseVO> resp = courseService.getCourse((Integer) session.getAttribute("id"));
        return JsonUtil.toJsonString(resp);
    }

}
