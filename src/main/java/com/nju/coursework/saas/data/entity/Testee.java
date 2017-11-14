package com.nju.coursework.saas.data.entity;

import javax.persistence.*;

/**
 * Created by zhouxiaofan on 2017/11/14.
 */
@Entity
public class Testee {
    private int id;
    private String studentMail;
    private Integer score;
    private Student student;
    private Exam exam;

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
    @Column(name = "student_mail", nullable = true, length = 30)
    public String getStudentMail() {
        return studentMail;
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Testee testee = (Testee) o;

        if (id != testee.id) return false;
        if (studentMail != null ? !studentMail.equals(testee.studentMail) : testee.studentMail != null) return false;
        if (score != null ? !score.equals(testee.score) : testee.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studentMail != null ? studentMail.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "student_no", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "examId", referencedColumnName = "id", nullable = false)
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
