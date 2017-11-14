package com.nju.coursework.saas.data.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by zhouxiaofan on 2017/11/14.
 */
@Entity
public class Student {
    private String name;
    private String studentNo;
    private String mail;
    private String password;
    private Collection<Testee> testee;
    private Collection<Answer> answer;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "student_no", nullable = false, length = 100)
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    @Basic
    @Column(name = "mail", nullable = false, length = 100)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (studentNo != null ? !studentNo.equals(student.studentNo) : student.studentNo != null) return false;
        if (mail != null ? !mail.equals(student.mail) : student.mail != null) return false;
        if (password != null ? !password.equals(student.password) : student.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (studentNo != null ? studentNo.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "student")
    public Collection<Testee> getTestee() {
        return testee;
    }

    public void setTestee(Collection<Testee> testee) {
        this.testee = testee;
    }

    @OneToMany(mappedBy = "student")
    public Collection<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(Collection<Answer> answer) {
        this.answer = answer;
    }
}
