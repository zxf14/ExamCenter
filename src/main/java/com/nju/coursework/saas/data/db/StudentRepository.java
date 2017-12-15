package com.nju.coursework.saas.data.db;

import com.nju.coursework.saas.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @Query("select us from Student us where us.studentNo=?1")
    List<Student> findByNo(String studentNo);

    @Query("select us from Student us where us.name=?1")
    List<Student> findByName(String studentName);

    @Query("select us from Student us where us.mail=?1")
    List<Student> findByEmail(String s);
}

