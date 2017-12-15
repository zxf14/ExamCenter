package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.ExamService;
import com.nju.coursework.saas.logic.vo.ExamVO;
import com.nju.coursework.saas.logic.vo.QuestionVO;
import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.util.JsonUtil;
import com.nju.coursework.saas.web.annotation.LoginRequired;
import com.nju.coursework.saas.web.response.GeneralResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test/Exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @LoginRequired
    @PostMapping("/config/create")
    public String createExam(int questionNum, @NonNull List<Integer> scores, int groupId,
                             List<QuestionVO> questions, @NonNull String startTime, @NonNull String endTime,
                             String title, String place, HttpSession session) {
        GeneralResponse response = examService.examConfig((Integer) session.getAttribute("id"), questionNum, scores,
                groupId, questions, startTime, endTime, title, place);
        return JsonUtil.toJsonString(response);
    }

    @LoginRequired
    @PostMapping("/config/import")
    public String importStudentToCreateExam(int quizCount, @RequestParam("file") MultipartFile file, String groupName,
                                            List<Integer> scores, List<QuestionVO> questions, String startTime, String endTime,
                                            String title, String place, HttpSession session) {
        GeneralResponse response;
        try {

            response = examService.examConfigByExcel((Integer) session.getAttribute("userId"), quizCount,
                    file.getInputStream(), groupName, scores, questions, startTime, endTime, title, place);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonUtil.toJsonString(new GeneralResponse(false, "excel文件导入出错"));
        }
        return JsonUtil.toJsonString(response);
    }

    @LoginRequired
    @PostMapping("/submit")
    public String submitExam(int testeeId, List<QuizVO> quiz) {
        GeneralResponse response = examService.submitExam(testeeId, quiz);
        return JsonUtil.toJsonString(response);
    }

    @LoginRequired
    @GetMapping("/teacherCreate")
    public String teacherCreate(List<Integer> examId) {
        List<ExamVO> examList = examService.createExamBefore(examId);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("examList", examList);
        return JsonUtil.toJsonString(resp);
    }

    @LoginRequired
    @GetMapping("/getExam")
    public String getExam(int examId, String studentNo) {
        ExamVO exam = examService.getExamAfter(examId, studentNo);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    @LoginRequired
    @GetMapping("/getExamList")
    public String getExamList(int examId) {
        List<ExamVO> exam = examService.getExamAfterList(examId);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    @LoginRequired
    @GetMapping("/getExamByCourse")
    public String getExamByCourse(int courseId) {
        List<ExamVO> exam = examService.getExamInfoByCourse(courseId);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    @LoginRequired
    @GetMapping("/getExamByStudentNo")
    public String getExamByStudentId(String studentNo) {
        List<ExamVO> exam = examService.getExamInfoByStudent(studentNo);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }


}
