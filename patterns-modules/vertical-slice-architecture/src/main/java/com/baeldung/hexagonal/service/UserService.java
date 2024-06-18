package com.baeldung.hexagonal.service;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.persistence.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
