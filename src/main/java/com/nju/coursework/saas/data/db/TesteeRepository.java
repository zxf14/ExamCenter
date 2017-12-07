package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Testee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteeRepository extends JpaRepository<Testee, Integer> {
}
