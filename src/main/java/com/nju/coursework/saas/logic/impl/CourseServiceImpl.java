package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.CourseRepository;
import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.Course;
import com.nju.coursework.saas.logic.service.CourseService;
import com.nju.coursework.saas.logic.vo.CourseVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CourseVO> getCourse(int userId) {
        List<CourseVO> courses = courseRepository.findByUserId(userId).stream().map(i -> new CourseVO(i)).collect(Collectors.toList());
        return courses;
    }
}
