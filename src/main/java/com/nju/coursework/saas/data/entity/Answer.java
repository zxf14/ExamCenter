package com.nju.coursework.saas.data.entity;

import javax.persistence.*;

/**
 * Created by zhouxiaofan on 2017/11/14.
 */
@Entity
public class Answer {
    private int id;
    private String content;
    private Quiz quiz;
    private Student student;

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
    @Column(name = "content", nullable = true, length = 100)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (id != answer.id) return false;
        if (content != null ? !content.equals(answer.content) : answer.content != null) return false;
        if (quiz != null ? !quiz.equals(answer.quiz) : answer.quiz != null) return false;
        return student != null ? student.equals(answer.student) : answer.student == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (quiz != null ? quiz.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "quizId", referencedColumnName = "id", nullable = false)
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "student_no", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
