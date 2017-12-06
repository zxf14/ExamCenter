package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.data.entity.Groups;
import com.nju.coursework.saas.logic.service.GroupService;
import com.nju.coursework.saas.logic.service.QuestionService;
import com.nju.coursework.saas.logic.vo.GroupsVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by guhan on 17/11/8.
 */
@Controller
@RequestMapping(value = "/v1/api")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping("/group/import")
    @ResponseBody
    public String createGroup(@RequestParam("file")MultipartFile file,
                                 @RequestParam("groupName") String groupName,
                                 HttpSession session) throws IOException {
        GeneralResponse resp = groupService.createGroup((Integer) session.getAttribute("id"),
                file.getInputStream(),
                groupName);
        String js = JsonUtil.toJsonString(resp);
        return js;
    }

    @GetMapping("/group/list")
    @ResponseBody
    public String getGroup(@RequestParam("groupName") String groupName,
                              HttpSession session) throws IOException {
        List<GroupsVO> resp = groupService.getGroups((Integer) session.getAttribute("id"));
        String js = JsonUtil.toJsonString(resp);
        return js;
    }

}
