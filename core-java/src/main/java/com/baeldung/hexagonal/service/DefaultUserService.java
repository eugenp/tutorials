package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.data.User;
import com.baeldung.hexagonal.secondary.ports.UserRepository;

public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String login(String uid, String pwd) {
        boolean loginSuccess = userRepository.fetch(uid, pwd);
        if (loginSuccess) {
            return "User is logged in successfully";
        } else {
            return "User not able to login due to incorrect username or password";
        }
    }

    @Override
    public String register(User user) {
        boolean registrationSuccess = userRepository.create(user);
        if (registrationSuccess) {
            return "User registration success";
        } else {
            return "User registration failed";
        }
    }

}
