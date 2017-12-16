package com.nju.coursework.saas.logic.vo;

import com.nju.coursework.saas.data.entity.Exam;
import com.nju.coursework.saas.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ExamVO {

    private List<QuestionVO> questions;
    private List<QuizVO> quizVOS;
    //考试id
    private int id;
    private String title;
    private String place;
    private String startTime;
    private String endTime;
    private int state = -1; //0为未开始，1为进行中，2为已结束, default为-1
    private double between;    //考试时长，以小时为单位
    //分值，与questions是一一对应的
    private List<Integer> value;

    //此字段用于考后生成试卷，存放学生选择的答案，与question是一一对应的；考前生成的试卷此列表为空
    private List<AnswerVO> answers;

    private Exam_Testee exam_testee;//此字段只有通过学生id获取考试列表时才生效

    public ExamVO(Exam exam) {
        init(exam);
    }

    public ExamVO(Exam exam, List<QuestionVO> questions, List<Integer> value, List<AnswerVO> answers) {
        init(exam);
        this.questions = new ArrayList<>(questions);
        this.value = new ArrayList<>(value);
        this.answers = new ArrayList<>(answers);
    }

    public ExamVO(Exam exam, List<QuizVO> questions) {
        init(exam);
        this.quizVOS = questions;
    }

    public ExamVO(Exam exam, List<QuizVO> questions, int testeeId, int state) {
        init(exam);
        this.quizVOS = questions;
        if (this.state == 1 && state != -1) {
            this.state = state;
        }
        this.exam_testee = new Exam_Testee(exam.getId(), testeeId);
    }

    private void init(Exam exam) {
        this.id = exam.getId();
        this.title = exam.getExamTitle();
        this.place = exam.getExamPlace();
        Instant timeStart = Timestamp.valueOf(exam.getStartTime()).toInstant();
        Instant timeEnd = Timestamp.valueOf(exam.getEndTime()).toInstant();
//        this.startTime = LocalDate.parse(DateTimeUtils.date(timeStart), DATE_FORMATTER);
//        this.endTime = LocalDate.parse(DateTimeUtils.date(timeEnd), DATE_FORMATTER);

        this.startTime = exam.getStartTime();
        this.endTime = exam.getEndTime();
        this.between = DateTimeUtils.between(timeStart, timeEnd, ChronoUnit.MILLIS);

        if (timeStart.compareTo(Instant.now()) > 0) {
            this.state = 0;
        } else if (timeEnd.compareTo(Instant.now()) < 0) {
            this.state = 2;
        } else if (timeStart.compareTo(Instant.now()) < 0 &&
                timeEnd.compareTo(Instant.now()) > 0) {
            this.state = 1;
        }
    }
}

@Data
@AllArgsConstructor
class Exam_Testee {
    private int examId;
    private int testeeId;
}
