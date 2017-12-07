package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Testee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TesteeRepository extends JpaRepository<Testee, Integer> {

    @Query("select t from Testee t where t.examByExamId.id=?1")
    List<Testee> findByExamId(int examId);
}

