package com.baeldung.pattern.hexagonal.service;

import com.baeldung.pattern.hexagonal.dao.User;
import com.baeldung.pattern.hexagonal.dao.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserProvider userProvider;

    public Optional<User> getUserByLogin(String login) {
        return userProvider.getUser(login);
    }
}
