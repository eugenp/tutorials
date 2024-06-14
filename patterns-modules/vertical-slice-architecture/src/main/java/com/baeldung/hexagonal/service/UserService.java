package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.persistence.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
