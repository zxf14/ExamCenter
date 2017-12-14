package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    @Query("select e from Exam e where e.courseById.id=?1")
    List<Exam> findByCourseId(int courseId);

}

