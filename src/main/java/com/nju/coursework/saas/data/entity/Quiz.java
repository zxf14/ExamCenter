package com.nju.coursework.saas.data.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
@Entity
public class Quiz {
    private int id;
    private Integer value;
    private Collection<Answer> answersById;
    private Exam examByExamId;
    private Question questionByQuestionId;
    private Testee testeeByTesteeId;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value", nullable = true)
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (id != quiz.id) return false;
        if (value != null ? !value.equals(quiz.value) : quiz.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "quizByQuizId")
    public Collection<Answer> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<Answer> answersById) {
        this.answersById = answersById;
    }

    @ManyToOne
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    public Exam getExamByExamId() {
        return examByExamId;
    }

    public void setExamByExamId(Exam examByExamId) {
        this.examByExamId = examByExamId;
    }

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    public Question getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(Question questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }

    @ManyToOne
    @JoinColumn(name = "testee_id", referencedColumnName = "id")
    public Testee getTesteeByTesteeId() {
        return testeeByTesteeId;
    }

    public void setTesteeByTesteeId(Testee testeeByTesteeId) {
        this.testeeByTesteeId = testeeByTesteeId;
    }

}
