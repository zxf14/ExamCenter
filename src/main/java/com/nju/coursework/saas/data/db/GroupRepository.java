package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Group;
import com.nju.coursework.saas.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}

