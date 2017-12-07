package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nju.coursework.saas.data.entity.Aoption;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class OptionVO {
    private int id;
    private String content;
    private Boolean isRight;

    public OptionVO() {
    }

    public OptionVO(Aoption i) {
        id = i.getId();
        content = i.getContent();
        isRight = i.getIsRight();
    }
}
