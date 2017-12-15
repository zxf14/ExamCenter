package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.logic.vo.ExamConfigVO;
import com.nju.coursework.saas.logic.vo.ExamVO;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import lombok.NonNull;

import java.io.InputStream;
import java.util.List;

public interface ExamService {

    GeneralResponse examConfig(int userId, ExamConfigVO examConfigVO);

    GeneralResponse examConfigByExcel(int userId, ExamConfigVO examConfigVO, InputStream excel);

    GeneralResponse submitExam(int testeeId, List<QuizVO> quiz);

    /**
     * 考前生成试卷
     *
     * @param examId 考试id
     * @return
     */
    List<ExamVO> createExamBefore(List<Integer> examId);

    /**
     * 考后生成试卷
     *
     * @param examId    考试id
     * @param studentId 学生id列表
     * @return
     */
    ExamVO getExamAfter(int examId, String studentId);

    /**
     * 批量生成所有考生的试卷
     *
     * @param examId
     * @return
     */
    List<ExamVO> getExamAfterList(int examId);

    /**
     * 根据课程Id获取该课程下的所有考试简介
     *
     * @param courseId
     * @return
     */
    List<ExamVO> getExamInfoByCourse(int courseId);

    /**
     * 根据学生Id获取该学生有关的所有考试简介
     *
     * @param studentNo
     * @return
     */
    List<ExamVO> getExamInfoByStudent(String studentNo);
}
