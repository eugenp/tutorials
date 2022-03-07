package com.baeldung.pattern.hexagonal2.domain.service;

import java.util.List;
import java.util.UUID;

import com.baeldung.pattern.hexagonal2.domain.model.User;
import com.baeldung.pattern.hexagonal2.port.primary.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User deleteUser(UUID id) {
        return null;
    }
}
