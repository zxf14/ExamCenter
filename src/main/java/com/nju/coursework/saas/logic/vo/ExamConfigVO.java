package com.nju.coursework.saas.logic.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ExamConfigVO {

    int questionNum;
    List<Integer> scores;
    int groupId;
    List<QuestionVO> questions;
    String groupName;
    String startTime;
    String endTime;
    String title;
    String place;
    int courseId;

}