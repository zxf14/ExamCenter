package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.CourseRepository;
import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.Course;
import com.nju.coursework.saas.data.entity.User;
import com.nju.coursework.saas.logic.service.CourseService;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public GeneralResponse createCourse(int userId, String courseName) {
        try {
            Course course = new Course();
            course.setUserByUserId(userRepository.findOne(userId));
            course.setName(courseName);
            courseRepository.saveAndFlush(course);
        } catch (Exception e) {
            return new GeneralResponse(false, e.getMessage());
        }

        return new GeneralResponse(true, "");
    }

    @Override
    public List<Course> getCourse(int userId) {
        User user = userRepository.findOne(userId);
        if (user != null) {
            List<Course> courses = (List<Course>) user.getCoursesById();
            return courses;
        } else {
            return null;
        }
    }
}
