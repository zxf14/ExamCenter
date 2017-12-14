package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Testee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TesteeRepository extends JpaRepository<Testee, Integer> {

    @Query("select t from Testee t where t.examByExamId.id=?1")
    List<Testee> findByExamId(int examId);
    @Query("select t from Testee t where t.studentByStudentId.id=?1")
    List<Testee> findByStudentId(String studentNo);
}

