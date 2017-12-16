package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.BaseTests;
import com.nju.coursework.saas.logic.service.CourseService;
import com.nju.coursework.saas.logic.service.ExamService;
import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.logic.vo.CourseVO;
import com.nju.coursework.saas.logic.vo.ExamConfigVO;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.logic.vo.QuizVO;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
public class ExamServiceImplTest extends  BaseTests{

    @Resource
    ExamService examService;
    @Resource
    StudentService studentService;

    @Ignore
    @Test
    public void createExam() {
        ExamConfigVO vo = new ExamConfigVO();
        vo.setCourseId(1);
        vo.setGroupId(1);
        vo.setQuestionNum(1);
        vo.setScores(Arrays.asList(0));
        vo.setGroupName("test");
        QuestionVO questionVO = new QuestionVO();
        vo.setQuestions(Arrays.asList(questionVO));
        vo.setStartTime("2017-12-16 14:14:21");
        vo.setEndTime("2017-12-17 12:12:12");
        vo.setTitle("test");
        vo.setPlace("abc");
        examService.examConfig(1, vo);
    }

    @Ignore
    @Test
    public void getExamByStudent() {
        System.out.println(examService.getExamInfoByStudent("1").size());
    }

    @Test
    public void getVerifyCode() {
        System.out.println(studentService.getVerifyCode("141250190@smail.nju.edu.cn"));
    }

}