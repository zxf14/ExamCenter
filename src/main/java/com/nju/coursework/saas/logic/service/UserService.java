package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.web.response.GeneralResponse;

public interface UserService {

    /**
     * 管理员（教师）登陆
     * @param username 用户名
     * @param password 密码
     * @return 登陆结果
     */
    GeneralResponse login(String username, String password);

}
