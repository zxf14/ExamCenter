package com.nju.coursework.saas.data.entity;

import javax.persistence.*;

/**
 * Created by zhouxiaofan on 2017/11/7.
 */
@Entity
@Table(name = "student", schema = "TestCenter", catalog = "")
public class Student {
    private String name;
    private String studentNo;
    private String password;
    private String mail;
    private String grade;
    private String classname;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Id
    @Column(name = "StudentNo", nullable = false, length = 100)
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    @Basic
    @Column(name = "classname", nullable = false, length = 100)
    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Basic
    @Column(name = "grade", nullable = false, length = 100)
    public String getGrade() {
        return classname;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (studentNo != null ? !studentNo.equals(student.studentNo) : student.studentNo != null) return false;
        if (password != null ? !password.equals(student.password) : student.password != null) return false;
        if (mail != null ? !mail.equals(student.mail) : student.mail != null) return false;
        if (grade != null ? !grade.equals(student.grade) : student.grade != null) return false;
        return classname != null ? classname.equals(student.classname) : student.classname == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (studentNo != null ? studentNo.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                " name='" + name + '\'' +
                ", studentNo='" + studentNo + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", grade='" + grade + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }
}
