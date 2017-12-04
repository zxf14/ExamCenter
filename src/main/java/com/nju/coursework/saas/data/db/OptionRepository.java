package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

}

