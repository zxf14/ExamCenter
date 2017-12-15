package com.nju.coursework.saas.logic.vo;

import com.nju.coursework.saas.data.entity.Answer;

public class AnswerVO {
    private String content;
    private int score;
    private int id;
    private int questionId;

    public AnswerVO(Answer answer) {
        this.content = answer.getContent();
        this.score = answer.getScore();
        this.id = answer.getId();
        this.questionId = answer.getQuizByQuizId().getQuestionByQuestionId().getId();
    }
}
