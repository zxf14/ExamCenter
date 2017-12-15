package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Groups;
import com.nju.coursework.saas.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Groups, Integer> {

    @Query("select groups from Groups groups where groups.userByUserId.id=?1")
    List<Groups> findByTeacher(int userId);
}

