package com.nju.coursework.saas.data.entity;

import javax.persistence.*;

/**
 * Created by zhouxiaofan on 2017/11/14.
 */
@Entity
public class Option {
    private int id;
    private String content;
    private Boolean isRight;
    private Question question;

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
    @Column(name = "content", nullable = true, length = 1000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "isRight", nullable = true)
    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (id != option.id) return false;
        if (content != null ? !content.equals(option.content) : option.content != null) return false;
        if (isRight != null ? !isRight.equals(option.isRight) : option.isRight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (isRight != null ? isRight.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
