package com.baeldung.example.service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.example.model.User;
import com.baeldung.example.validator.UserValidator;

@Service
public class UserService {

    @Resource
    private Map<String, User> configuredUsers;

    @Autowired
    private UserValidator userValidator;

    public Set<User> findAll() {
        return configuredUsers.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    public Optional<User> findUserByName(String userName) {
        return Optional.ofNullable(configuredUsers.get(userName));
    }

    public User updateUser(String userName, User newUser) {
        userValidator.validate(userName, newUser);
        return configuredUsers.put(userName, newUser);
    }

    public boolean createUser(User newUser) {
        userValidator.validate(newUser);
        configuredUsers.put(newUser.getName(), newUser);
        return true;
    }

    public boolean deleteUser(String userName) {
        if (configuredUsers.containsKey(userName)) {
            configuredUsers.remove(userName);
            return true;
        }
        return false;
    }
}
