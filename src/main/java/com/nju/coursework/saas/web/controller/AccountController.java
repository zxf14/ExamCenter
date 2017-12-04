package com.nju.coursework.saas.web.controller;

import com.alibaba.fastjson.JSON;
import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.response.GeneralResponse;
import com.nju.coursework.saas.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guhan on 17/11/8.
 */
@Controller
public class AccountController {

    @Autowired
    UserService userService;    /*管理员、教师*/

    @Autowired
    StudentService studentService;      /*学生*/

    @PostMapping("/teacher/login")
    @ResponseBody
    public String loginTeacher(String username, String password){
        GeneralResponse resp = userService.login(username, password);
        String js = JsonUtil.toJsonString(resp);
        return js;
    }

    @PostMapping("/student/register")
    @ResponseBody
    public String registerStudent(String name, String id, String email, String password){
        GeneralResponse resp = studentService.register(new StudentVO(name, id, password, email));
        String js = JsonUtil.toJsonString(resp);
        return js;
    }

    @PostMapping("/student/login")
    @ResponseBody
    public String loginStudent(String id, String password){
        GeneralResponse resp =  studentService.login(id, password);
        String js = JsonUtil.toJsonString(resp);
        return js;
    }
}
