package com.nju.coursework.saas.logic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nju.coursework.saas.data.entity.Groups;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class GroupsVO {
    private String name;
    private List<String> students;
    private int id;

    public GroupsVO() {
    }

    public GroupsVO(Groups groups) {
        id = groups.getId();
        name = groups.getName();
        if (groups.getStudents()!=null)
            students = Arrays.asList(groups.getStudents().split(";"));
        else {
            students = new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
