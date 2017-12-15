package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query("select t from Answer t where t.studentByStudentId.id=?1")
    List<Answer> findByStudentId(String studentNo);
}

