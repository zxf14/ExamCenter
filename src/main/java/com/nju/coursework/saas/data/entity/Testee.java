package com.nju.coursework.saas.data.entity;

import javax.persistence.*;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
@Entity
public class Testee {
    private int id;
    private String studentMail;
    private String studentName;
    private Integer score;
    private Student studentByStudentId;
    private Exam examByExamId;

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
    @JoinColumn(name = "student_id", referencedColumnName = "student_no", nullable = false)
    public Student getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(Student studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }

    @ManyToOne
    @JoinColumn(name = "exam_id", referencedColumnName = "id", nullable = false)
    public Exam getExamByExamId() {
        return examByExamId;
    }

    public void setExamByExamId(Exam examByExamId) {
        this.examByExamId = examByExamId;
    }

    public boolean hasEmail() {
        return studentMail != null;
    }

    @Basic
    @Column(name = "student_name", nullable = true, length = 30)
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
