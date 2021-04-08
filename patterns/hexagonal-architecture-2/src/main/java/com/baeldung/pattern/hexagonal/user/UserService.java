package com.baeldung.pattern.hexagonal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserProvider userProvider;

    public User getUserByLogin(String login) {
        return userProvider.getUser(login);
    }
}
