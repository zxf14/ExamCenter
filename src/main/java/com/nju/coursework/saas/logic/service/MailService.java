package com.nju.coursework.saas.logic.service;

public interface MailService {

    void validateMail(String userMail, String verifyCode);

    void examKeyMail(String userMail, String key, String examTitle);

    void scoreMail(String userMail, String examTitle, int score);

}
