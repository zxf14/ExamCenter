package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.logic.vo.ExamVO;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import lombok.NonNull;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public interface ExamService {
    /**
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
                               @NonNull List<StudentVO> testees, @NonNull List<QuestionVO> questions,
                               @NonNull Timestamp startTime, @NonNull Timestamp endTime,
                               String title, String place);

    /**
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
                                      @NonNull Timestamp startTime, @NonNull Timestamp endTime,
                                      String title, String place);

    GeneralResponse submitExam(int testeeId, List<QuizVO> quiz);

    /**
     *考前生成试卷
     * @param examId 考试id
     * @return
     */
    List<ExamVO> createExamBefore(List<Integer> examId);

    /**
     * 考后生成试卷
     * @param examId 考试id
     * @param studentId 学生id列表
     * @return
     */
    ExamVO getExamAfter(int examId, String studentId);

    /**
     * 批量生成所有考生的试卷
     * @param examId
     * @return
     */
    List<ExamVO> getExamAfterList(int examId);

    /**
     * 根据课程Id获取该课程下的所有考试简介
     * @param courseId
     * @return
     */
    List<ExamVO> getExamInfoByCourse(int courseId);

    /**
     * 根据学生Id获取该学生有关的所有考试简介
     * @param studentNo
     * @return
     */
    List<ExamVO> getExamInfoByStudent(String studentNo);
}
