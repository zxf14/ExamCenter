package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nju.coursework.saas.data.entity.Aoption;
import com.nju.coursework.saas.data.entity.Course;
import com.nju.coursework.saas.data.entity.User;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CourseVO {
    private String name;
    private int id;

    public CourseVO() {
    }

    public CourseVO(Course i) {
        id = i.getId();
        name = i.getName();
    }
}
