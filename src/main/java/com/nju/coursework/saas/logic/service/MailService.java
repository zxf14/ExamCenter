package com.nju.coursework.saas.logic.service;

public interface MailService {

    void validateMail(String userMail);

    void examKeyMail(String userMail);

    void scoreMail(String userMail, String examTitle, int score);

}
