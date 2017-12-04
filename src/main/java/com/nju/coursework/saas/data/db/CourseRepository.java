package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("select us from Course us where us.userByUserId.id=?1")
    List<Course> findByUserId(int id);
}

