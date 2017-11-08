package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.web.response.GeneralResponse;

public interface UserService {

    GeneralResponse register(String userMail, String password);

    GeneralResponse login(String userMail, String password);

}
