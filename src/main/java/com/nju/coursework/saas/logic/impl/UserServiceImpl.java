package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.User;
import com.nju.coursework.saas.logic.service.UserService;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guhan on 17/11/8.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public GeneralResponse login(String username, String password) {
        List<User> users = userRepository.findByName(username);
        if (users.size() == 0)
            return new GeneralResponse(false, "用户不存在");
        if (users.get(0).getPassword().equals(password))
            return new GeneralResponse(true, "");
        else
            return new GeneralResponse(false, "密码错误");
    }

}
