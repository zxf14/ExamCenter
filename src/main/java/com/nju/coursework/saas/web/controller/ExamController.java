package com.nju.coursework.saas.web.controller;

import com.nju.coursework.saas.logic.service.ExamService;
import com.nju.coursework.saas.logic.vo.ExamConfigVO;
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
@RequestMapping("/test/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @LoginRequired
    @PostMapping("/config/create")
    public String createExam(@RequestBody ExamConfigVO examConfigVO, HttpSession session) {
        GeneralResponse response = examService.examConfig((Integer) session.getAttribute("id"), examConfigVO);
        return JsonUtil.toJsonString(response);
    }

    @LoginRequired
    @PostMapping("/config/import")
    public String importStudentToCreateExam(@RequestBody ExamConfigVO examConfigVO, @RequestParam("file") MultipartFile file, HttpSession session) {
        GeneralResponse response;
        try {

            response = examService.examConfigByExcel((Integer) session.getAttribute("id"), examConfigVO, file.getInputStream());
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
