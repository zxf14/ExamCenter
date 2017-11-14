package com.nju.coursework.saas.data.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by zhouxiaofan on 2017/11/14.
 */
@Entity
public class Question {
    private String content;
    private int id;
    private Integer type;
    private Collection<Quiz> quiz;
    private Collection<Option> option;

    @Basic
    @Column(name = "content", nullable = true, length = 10000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (content != null ? !content.equals(question.content) : question.content != null) return false;
        if (type != null ? !type.equals(question.type) : question.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "question")
    public Collection<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(Collection<Quiz> quiz) {
        this.quiz = quiz;
    }

    @OneToMany(mappedBy = "question")
    public Collection<Option> getOption() {
        return option;
    }

    public void setOption(Collection<Option> option) {
        this.option = option;
    }
}
