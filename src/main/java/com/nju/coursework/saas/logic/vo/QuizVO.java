package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nju.coursework.saas.data.entity.Aoption;
import com.nju.coursework.saas.data.entity.Question;
import com.nju.coursework.saas.data.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class QuizVO {
    private Integer value;
    private List<String> optionId;
    private QuestionVO question;
    private int id;

    public QuizVO(Quiz quiz, Question question) {
        this.value = quiz.getValue();
        this.question = new QuestionVO(question);
    }
}
