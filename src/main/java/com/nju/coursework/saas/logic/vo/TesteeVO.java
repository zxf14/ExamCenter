package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nju.coursework.saas.data.entity.Testee;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class TesteeVO {
    private int id;
    private String studentMail;
    private String studentName;
    private Integer score;
    private String studentId;
    private int examId;

    public TesteeVO() {

    }

    public TesteeVO(Testee testee) {
        if (testee.getExamByExamId()!=null) examId = testee.getExamByExamId().getId();
        if (testee.getStudentByStudentId()!=null) studentId = testee.getStudentByStudentId().getStudentNo();
        id = testee.getId();
        studentMail = testee.getStudentMail();
        studentName = testee.getStudentName();
        score = testee.getScore();
    }
}
