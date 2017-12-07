package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.ExamConfigService;
import com.nju.coursework.saas.logic.service.SubmitExamService;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.response.GeneralResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/Exam")
public class ExamController {

    @Autowired
    private ExamConfigService examConfigService;

    @Autowired
    private SubmitExamService submitExamService;

    @PostMapping("/config/create")
    public String createExam(int questionNum, @NonNull List<Integer> scores, @RequestParam List<StudentVO> testees,
                             List<QuestionVO> questions, @NonNull Timestamp startTime, @NonNull Timestamp endTime,
                             HttpSession session) {
        GeneralResponse response = examConfigService.examConfig((Integer) session.getAttribute("userId"), questionNum, scores,
                testees, questions, startTime, endTime);
        return JsonUtil.toJsonString(response);
    }

    @PostMapping("/config/import")
    public String importStudentToCreateExam(int quizCount, @RequestParam("file") MultipartFile file, String groupName,
                                            List<Integer> scores, List<QuestionVO> questions, Timestamp startTime, Timestamp endTime, HttpSession session) {
        GeneralResponse response;
        try {

            response = examConfigService.examConfigByExcel((Integer) session.getAttribute("userId"), quizCount,
                    file.getInputStream(), groupName, scores, questions, startTime, endTime);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonUtil.toJsonString(new GeneralResponse(false, "excel文件导入出错"));
        }
        return JsonUtil.toJsonString(response);
    }

    @PostMapping("/submit")
    public String submitExam(int testeeId, List<QuizVO> quiz) {
        GeneralResponse response = submitExamService.submitExam(testeeId, quiz);
        return JsonUtil.toJsonString(response);
    }

}
