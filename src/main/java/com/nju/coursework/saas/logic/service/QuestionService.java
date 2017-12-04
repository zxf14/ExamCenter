package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.web.response.GeneralResponse;

import java.io.InputStream;

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


}
