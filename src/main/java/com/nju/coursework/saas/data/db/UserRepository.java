package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select us from User us where us.userName=?1")
    List<User> findByName(String userName);
}

