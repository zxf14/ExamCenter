package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.logic.vo.TesteeVO;

import java.util.List;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
public interface TesteeService {
    /**
     * 获取某次考试成绩单
     * @param examId
     */
    List<TesteeVO> getTesteeReport(int examId);
}
