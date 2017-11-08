package com.nju.coursework.saas.service;

import com.nju.coursework.saas.entity.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by guhan on 17/11/8.
 */
@Service
public class UserService {

    @Autowired
    MailService mailService;

    public GeneralResponse register(String userMail, String password){
        //...
        mailService.validateMail(userMail);
        //...
        return null;
    }

    public GeneralResponse login(String userMail, String password){
        return null;
    }

}
