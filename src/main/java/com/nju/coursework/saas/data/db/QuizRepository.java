package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Query("select t from Quiz t where t.examByExamId.id=?1")
    List<Quiz> findByExamId(int examId);
}

