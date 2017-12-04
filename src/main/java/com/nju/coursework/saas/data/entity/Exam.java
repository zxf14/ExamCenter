package com.nju.coursework.saas.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
@Entity
public class Exam {
    private int id;
    private Timestamp startTime;
    private Timestamp endTime;
    private String subject;
    private User userByUserId;
    private Collection<Quiz> quizzesById;
    private Collection<Testee> testeesById;

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
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "subject", nullable = true, length = 20)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exam exam = (Exam) o;

        if (id != exam.id) return false;
        if (startTime != null ? !startTime.equals(exam.startTime) : exam.startTime != null) return false;
        if (endTime != null ? !endTime.equals(exam.endTime) : exam.endTime != null) return false;
        if (subject != null ? !subject.equals(exam.subject) : exam.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @OneToMany(mappedBy = "examByExamId")
    public Collection<Quiz> getQuizzesById() {
        return quizzesById;
    }

    public void setQuizzesById(Collection<Quiz> quizzesById) {
        this.quizzesById = quizzesById;
    }

    @OneToMany(mappedBy = "examByExamId")
    public Collection<Testee> getTesteesById() {
        return testeesById;
    }

    public void setTesteesById(Collection<Testee> testeesById) {
        this.testeesById = testeesById;
    }
}
