package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.data.User;
import com.baeldung.hexagonal.secondary.ports.UserRepository;

public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(String uid, String pwd) {
        boolean loginSuccess = userRepository.fetch(uid, pwd);
        if (loginSuccess) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean register(User user) {
        boolean registrationSuccess = userRepository.create(user);
        if (registrationSuccess) {
            return true;
        } else {
            return false;
        }
    }

}
