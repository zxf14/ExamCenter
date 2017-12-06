package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.logic.service.UserService;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
    public String loginTeacher(String username, String password, HttpSession session){
        GeneralResponse resp = userService.login(username, password);
        String js = JsonUtil.toJsonString(resp);
        if (resp.isSuccess()){
            session.setAttribute("id", resp.getData().get("id"));
        }
        return js;
    }

    @PostMapping("/student/register")
    @ResponseBody
    public String registerStudent(String name, String id, String email, String password, HttpSession session){
        GeneralResponse resp = studentService.register(new StudentVO(name, id, password, email));
        String js = JsonUtil.toJsonString(resp);
        if (resp.isSuccess()){
            session.setAttribute("id", id);
        }
        return js;
    }

    @PostMapping("/student/login")
    @ResponseBody
    public String loginStudent(String id, String password, HttpSession session){
        GeneralResponse resp =  studentService.login(id, password);
        if (resp.isSuccess()){
            session.setAttribute("id", id);
        }
        String js = JsonUtil.toJsonString(resp);
        return js;
    }
}
