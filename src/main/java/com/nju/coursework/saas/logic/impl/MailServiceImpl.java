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

    public void validateMail(String userMail, String verifyCode) {
        mailMaster.sendForValidation(userMail, verifyCode);
    }

    @Override
    public void examKeyMail(String userMail, String key, String examTitle) {
        mailMaster.sendForExam(userMail, key, examTitle);
    }

    @Override
    public void scoreMail(String userMail, String examTitle, int score) {
        mailMaster.sendForResult(userMail, examTitle, score);
    }

}
