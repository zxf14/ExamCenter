package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.data.entity.Course;
import com.nju.coursework.saas.logic.vo.CourseVO;
import com.nju.coursework.saas.web.response.GeneralResponse;

import java.util.List;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
public interface CourseService {
    /**
     * 创建课程
     * @param userId
     * @param courseName
     * @return
     */
    GeneralResponse createCourse(int userId, String courseName);

    List<CourseVO> getCourse(int userId);
}
