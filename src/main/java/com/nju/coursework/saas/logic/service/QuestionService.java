package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.data.entity.Question;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.web.response.GeneralResponse;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
public interface QuestionService {

    /**
     * 导入试题
     * @param excel     excel文件
     * @param courseId
     * @return
     */
    GeneralResponse importQuestion(InputStream excel, int courseId);

    /**
     * 获取题库
     * @param courseId 课程id
     * @return 题库列表
     */
    List<QuestionVO> getQuestions(int courseId);
}
