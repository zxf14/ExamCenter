package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.CourseService;
import com.nju.coursework.saas.logic.service.TesteeService;
import com.nju.coursework.saas.logic.vo.CourseVO;
import com.nju.coursework.saas.logic.vo.TesteeVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.annotation.LoginRequired;
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
@RequestMapping(value = "/test/testee")
public class TesteeController {

    @Autowired
    TesteeService testeeService;

    @LoginRequired
    @PostMapping("/list")
    @ResponseBody
    public String getTesteesReport(int examId, HttpSession session) throws IOException {
        List<TesteeVO> testeeVOS = testeeService.getTesteeReport(examId);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("report",testeeVOS);
        return JsonUtil.toJsonString(resp);
    }

}
