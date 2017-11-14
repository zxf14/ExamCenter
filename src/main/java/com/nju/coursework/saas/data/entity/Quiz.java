package com.nju.coursework.saas.data.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by zhouxiaofan on 2017/11/14.
 */
@Entity
public class Quiz {
    private int id;
    private Integer value;
    private Exam exam;
    private Question question;
    private Collection<Answer> answer;

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

    @ManyToOne
    @JoinColumn(name = "examId", referencedColumnName = "id")
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @OneToMany(mappedBy = "quiz")
    public Collection<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(Collection<Answer> answer) {
        this.answer = answer;
    }
}
