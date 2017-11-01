package com.nju.coursework.saas.db;

import com.nju.coursework.saas.entity.User;

import javax.transaction.Transactional;

/**
 * Created by guhan on 17/11/1.
 */
@Transactional
public interface UserDao {
    /**
     *
     * @param email
     * @return
     */
    public User findByEmail(String email);
}
