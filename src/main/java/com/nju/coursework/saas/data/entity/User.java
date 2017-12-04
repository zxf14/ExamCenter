package com.nju.coursework.saas.data.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by zhouxiaofan on 2017/12/4.
 */
@Entity
public class User {
    private int id;
    private String userName;
    private String password;
    private Collection<Course> coursesById;
    private Collection<Exam> examsById;
    private Collection<Groups> groupssById;

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
    @Column(name = "user_name", nullable = false, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Course> getCoursesById() {
        return coursesById;
    }

    public void setCoursesById(Collection<Course> coursesById) {
        this.coursesById = coursesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Exam> getExamsById() {
        return examsById;
    }

    public void setExamsById(Collection<Exam> examsById) {
        this.examsById = examsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Groups> getGroupssById() {
        return groupssById;
    }

    public void setGroupssById(Collection<Groups> groupssById) {
        this.groupssById = groupssById;
    }
}
