package com.nju.coursework.saas.data.entity;

import javax.persistence.*;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
@Entity
public class Answer {
    private String content;
    private int score;
    private int id;
    private Quiz quizByQuizId;
    private Student studentByStudentId;

    @Basic
    @Column(name = "content", nullable = true, length = 100)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (id != answer.id) return false;
        if (content != null ? !content.equals(answer.content) : answer.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
    public Quiz getQuizByQuizId() {
        return quizByQuizId;
    }

    public void setQuizByQuizId(Quiz quizByQuizId) {
        this.quizByQuizId = quizByQuizId;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_no", nullable = false)
    public Student getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(Student studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
