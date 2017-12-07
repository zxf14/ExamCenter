package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.web.response.GeneralResponse;

import java.util.List;

public interface SubmitExamService {

    GeneralResponse submitExam(int testeeId, List<QuizVO> quiz);
}
