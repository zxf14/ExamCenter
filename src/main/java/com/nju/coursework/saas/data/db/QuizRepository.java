package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

}

