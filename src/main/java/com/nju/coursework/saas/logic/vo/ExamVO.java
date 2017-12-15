package com.nju.coursework.saas.logic.vo;

import com.nju.coursework.saas.data.entity.Answer;
import com.nju.coursework.saas.data.entity.Exam;
import com.nju.coursework.saas.data.entity.Question;
import com.nju.coursework.saas.data.entity.Student;
import com.nju.coursework.saas.util.DateTimeUtils;
import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExamVO {

    private List<QuestionVO> questions;
    //考试id
    private int id;
    private String title;
    private String place;
    private String startTime;
    private int state = -1; //0为未开始，1为进行中，2为已结束, default为-1
    private int between;    //考试时长，以小时为单位
    //分值，与questions是一一对应的
    private List<Integer> value;

    //此字段用于考后生成试卷，存放学生选择的答案，与question是一一对应的；考前生成的试卷此列表为空
    private List<AnswerVO> answers;

    public ExamVO(Exam exam) {
        init(exam);
    }

    public ExamVO(Exam exam, List<QuestionVO> questions, List<Integer>value, List<AnswerVO> answers) {
        init(exam);
        this.questions = new ArrayList<>(questions);
        this.value = new ArrayList<>(value);
        this.answers = new ArrayList<>(answers);
    }

    private void init(Exam exam) {
        this.id = exam.getId();
        this.title = exam.getExamTitle();
        this.place = exam.getExamPlace();
        this.startTime = DateTimeUtils.dateTime(exam.getStartTime().toInstant());
        this.between = DateTimeUtils.between(exam.getStartTime().toInstant(), exam.getEndTime().toInstant(), ChronoUnit.HOURS);
        if (exam.getStartTime().toInstant().compareTo(Instant.now()) > 0) {
            this.state = 0;
        } else if(exam.getEndTime().toInstant().compareTo(Instant.now()) < 0) {
            this.state = 2;
        } else if(exam.getStartTime().toInstant().compareTo(Instant.now()) < 0 &&
                exam.getEndTime().toInstant().compareTo(Instant.now()) > 0) {
            this.state = 1;
        }
    }

}
