package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.data.entity.Groups;
import com.nju.coursework.saas.web.response.GeneralResponse;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
public interface QuestionService {

    /**
     * 导入试题
     *
     * @param userId    创建者id
     * @param excel     excel文件在resources下路径
     * @param groupName 群组名，一般为班级或者年级名
     * @return
     */
    GeneralResponse importQuestion(int userId, InputStream excel, String groupName);


}
