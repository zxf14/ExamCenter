package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Question;
import com.nju.coursework.saas.data.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("select q from Question q where q.courseByCourseId.id=?1")
    List<Question> findByCourseId(int courseId);
}

