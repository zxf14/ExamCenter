package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import lombok.NonNull;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public interface ExamConfigService {


    /**
     *
     * @param userId
     * @param questionNum
     * @param scores
     * @param testees
     * @param questions
     * @param startTime
     * @param endTime
     * @return
     */
    GeneralResponse examConfig(int userId, int questionNum, @NonNull List<Integer> scores,
                               @NonNull List<StudentVO> testees,@NonNull List<QuestionVO> questions,
                               @NonNull Timestamp startTime, @NonNull Timestamp endTime);

    /**
     *
     * @param userId
     * @param questionNum
     * @param excel
     * @param groupName
     * @param scores
     * @param startTime
     * @param endTime
     * @return
     */
    GeneralResponse examConfigByExcel(int userId, int questionNum, InputStream excel, @NonNull String groupName,
                                      @NonNull List<Integer> scores, @NonNull List<QuestionVO> questions,
                                      @NonNull Timestamp startTime, @NonNull Timestamp endTime);
}
