package com.rabbitmq.lihongjie.demo.service;

import com.rabbitmq.lihongjie.demo.dao.UserDao;
import com.rabbitmq.lihongjie.demo.model.Account;
import com.rabbitmq.lihongjie.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public int insert(Account account) {

        User user = new User();
        user.setName(account.getName());
        return userDao.insertUser(user);
    }
}
