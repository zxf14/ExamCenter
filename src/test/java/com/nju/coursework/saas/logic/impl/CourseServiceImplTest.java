package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.BaseTests;
import com.nju.coursework.saas.data.entity.Course;
import com.nju.coursework.saas.logic.service.CourseService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
public class CourseServiceImplTest extends BaseTests {
    @Resource
    CourseService courseService;

    @Test
    public void createCourse() throws Exception {
        courseService.createCourse(1, "course111");
        courseService.createCourse(1, "course22");
    }

    @Test
    public void getCourse() throws Exception {
        List<Course> courses = courseService.getCourse(2);
        System.out.println(courses);
    }

}