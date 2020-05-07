package com.baeldung.hexagonal.architecture.core.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.core.domain.User;
import com.baeldung.hexagonal.architecture.port.input.UserService;
import com.baeldung.hexagonal.architecture.port.output.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
