package com.baeldung.hexagonal.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.entity.User;
import com.baeldung.hexagonal.domain.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser() {
        // Business logic goes here, for simplicity returning user from repository
        return userRepository.readUser();
    }

}
