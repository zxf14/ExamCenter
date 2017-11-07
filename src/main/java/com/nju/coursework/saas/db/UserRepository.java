package com.nju.coursework.saas.db;

import com.nju.coursework.saas.entity.Student;
import com.nju.coursework.saas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select us from User us where us.userName=?1")
    public User findByEmail(String email);
}

