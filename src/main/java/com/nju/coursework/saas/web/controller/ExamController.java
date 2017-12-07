package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.ExamConfigService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ConfigExam")
public class ExamController {

    @Autowired
    private ExamConfigService examConfigService;

    @PostMapping("/create")
    public String createExam(int questionNum, @NonNull List<Integer> questions, @RequestParam List<StudentVO> testees,
                             @NonNull Timestamp startTime, @NonNull Timestamp endTime, HttpSession session) {
        List<QuizVO> quiz = new ArrayList<>();
        questions.stream().forEach(
                q -> quiz.add(new QuizVO(q)));
         GeneralResponse response = examConfigService.examConfig((Integer)session.getAttribute("userId"),questionNum, quiz,testees,
                startTime, endTime);
         return JsonUtil.toJsonString(response);
    }

    @PostMapping("/import")
    public String importStudentToCreateExam(int quizCount, @RequestParam("file") MultipartFile file, String groupName,
                                            List<Integer> questions, Timestamp startTime, Timestamp endTime, HttpSession session) {
        GeneralResponse response;
        try {
            List<QuizVO> quiz = new ArrayList<>();
            questions.stream().forEach(
                    q -> quiz.add(new QuizVO(q)));
            response = examConfigService.examConfigByExcel((Integer)session.getAttribute("userId"), quizCount,
                    file.getInputStream(),groupName, quiz, startTime, endTime);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonUtil.toJsonString(new GeneralResponse(false, "excel文件导入出错"));
        }
        return JsonUtil.toJsonString(response);
    }
}
