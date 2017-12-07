package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentVO {
    private String name;
    private String studentNo;
    private String password;
    private String mail;

    public StudentVO(String name, String studentNo, String password, String mail) {
        this.name = name;
        this.studentNo = studentNo;
        this.password = password;
        this.mail = mail;
    }

    public StudentVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
