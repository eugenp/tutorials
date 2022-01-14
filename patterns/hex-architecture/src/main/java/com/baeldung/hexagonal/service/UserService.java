package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.model.User;
import com.baeldung.hexagonal.port.UserDatabasePort;

public class UserService {

    private UserDatabasePort userDatabase;

    public UserService(UserDatabasePort userDatabase) {
        this.userDatabase = userDatabase;
    }

    public User findUser(String username) {
        return userDatabase.getUser(username);
    }
}