package com.baeldung.hexagonal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.repository.UserDaoInterface;
import com.baeldung.hexagonal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoInterface userDao;

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

}
