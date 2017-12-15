package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.logic.service.MailService;
import com.nju.coursework.saas.util.MailMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by guhan on 17/11/8.
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    MailMaster mailMaster;

    public void validateMail(String userMail) {
        mailMaster.sendForValidation(userMail, "https://localhost:8080/student/login");
    }

    @Override
    public void examKeyMail(String userMail) {

    }

    @Override
    public void scoreMail(String userMail, String examTitle, int score) {
        mailMaster.sendForResult(userMail, examTitle, score);
    }

}
