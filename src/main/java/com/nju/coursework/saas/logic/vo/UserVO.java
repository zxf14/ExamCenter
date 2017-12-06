package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
