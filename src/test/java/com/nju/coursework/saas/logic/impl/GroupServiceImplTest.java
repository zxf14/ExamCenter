package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.BaseTests;
import com.nju.coursework.saas.logic.service.GroupService;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * Created by zhouxiaofan on 2017/12/2.
 */
public class GroupServiceImplTest extends BaseTests {
    @Resource
    GroupService groupService;

    @Test
    public void createGroup() throws Exception {
        groupService.createGroup(1, new ClassPathResource("static/studentList.xlsx").getInputStream(),"name");
    }

    @Test
    public void getGroups() throws Exception {
        System.out.println(groupService.getGroups(1));

    }

}