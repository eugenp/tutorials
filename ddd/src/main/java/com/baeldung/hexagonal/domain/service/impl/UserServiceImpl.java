package com.baeldung.hexagonal.domain.service.impl;

import com.baeldung.hexagonal.domain.entities.User;
import com.baeldung.hexagonal.domain.service.UserService;
import com.baeldung.hexagonal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(@Qualifier("mongodb_userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public String getEmail(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getEmail).orElse("");
    }
}
