package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import lombok.NonNull;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public interface ExamConfigService {


    /**
     * @param userId
     * @param questionNum
     * @param questions
     * @param testees
     * @param startTime
     * @param endTime
     * @return
     */
    GeneralResponse examConfig(int userId, int questionNum, @NonNull List<QuizVO> questions, @NonNull List<StudentVO> testees,
                               @NonNull Timestamp startTime, @NonNull Timestamp endTime);

    /**
     * 通过导入excel来配置参加考试的群组
     *
     * @param userId        教师id
     * @param questionNum   试题总数
     * @param excel         导入学生名单的excel
     * @param groupName     群组名字，班级名或年级名
     * @param questionScore 每题分数
     * @param startTime     考试开始时间
     * @param endTime       考试结束时间
     * @return
     */
    GeneralResponse examConfigByExcel(int userId, int questionNum, InputStream excel, @NonNull String groupName,
                                      @NonNull List<QuizVO> questionScore,
                                      @NonNull Timestamp startTime, @NonNull Timestamp endTime);
}
