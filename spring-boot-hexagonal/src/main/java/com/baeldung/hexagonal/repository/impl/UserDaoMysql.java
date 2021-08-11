package com.baeldung.hexagonal.repository.impl;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.repository.UserDaoInterface;

@Component
public class UserDaoMysql implements UserDaoInterface {

    @Override
    public User getUserById(int id) {
        // TODO implement MySQL logic here
        return null;
    }

}
