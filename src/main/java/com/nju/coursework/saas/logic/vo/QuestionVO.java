package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nju.coursework.saas.data.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class QuestionVO {
    private String content;
    private int id;
    private Integer type;//0单选1多选
    private List<OptionVO> optionVOList;

    public QuestionVO() {
    }

    public QuestionVO(Question item) {
        this.id = item.getId();
        this.content = item.getContent();
        this.type = item.getType();
        this.optionVOList = item.getAoptionsById().stream().map(i -> new OptionVO(i)).collect(Collectors.toList());
    }
}
