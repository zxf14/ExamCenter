package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Aoption;
import com.nju.coursework.saas.data.entity.Groups;
import com.nju.coursework.saas.data.entity.Question;
import com.nju.coursework.saas.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OptionRepository extends JpaRepository<Aoption, Integer> {
    @Query("select o from Aoption o where o.questionByQuestionId.id=?1")
    List<Aoption> findByQuestion(int qId);
}

