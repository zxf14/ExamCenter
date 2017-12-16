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
    public String submitExam(int testeeId, @RequestBody List<QuizVO> quiz) {
        GeneralResponse response = examService.submitExam(testeeId, quiz);
        return JsonUtil.toJsonString(response);
    }

    /**
     * 考前教师生成试卷
     * @param examId
     * @return
     */
    @LoginRequired
    @GetMapping("/teacherCreate")
    public String teacherCreate(Integer examId) {
        ExamVO exam = examService.createExamBefore(examId);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    /**
     * 考后生成试卷
     * @param examId 考试id
     * @param studentNo 学生编号
     * @return
     */
    @LoginRequired
    @GetMapping("/getExam")
    public String getExam(int examId, String studentNo) {
        ExamVO exam = examService.getExamAfter(examId, studentNo);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    /**
     * 考后批量生成该考试下的所有试卷
     * @param examId
     * @return
     */
    @LoginRequired
    @GetMapping("/getExamList")
    public String getExamList(int examId) {
        List<ExamVO> exam = examService.getExamAfterList(examId);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    /**
     * 通过课程id获取该课程下所有的考试列表
     * @param courseId
     * @return
     */
    @LoginRequired
    @GetMapping("/getExamByCourse")
    public String getExamByCourse(int courseId) {
        List<ExamVO> exam = examService.getExamInfoByCourse(courseId);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    /**
     * 通过学生id获得学生相关的所有考试列表
     * @param studentNo
     * @return
     */
    @LoginRequired
    @GetMapping("/getExamByStudentNo")
    public String getExamByStudentId(String studentNo) {
        List<ExamVO> exam = examService.getExamInfoByStudent(studentNo);
        GeneralResponse resp = new GeneralResponse(true, "");
        resp.putDate("exam", exam);
        return JsonUtil.toJsonString(resp);
    }

    @LoginRequired
    @GetMapping("/attendExam")
    public String attendExam(int testeeId, String password) {
         GeneralResponse resp = new GeneralResponse(true, "");
         resp.putDate("validation", examService.attendExam(testeeId, password));
         return JsonUtil.toJsonString(resp);
    }


}
