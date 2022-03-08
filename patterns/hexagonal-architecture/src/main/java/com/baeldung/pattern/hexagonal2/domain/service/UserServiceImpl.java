package com.baeldung.pattern.hexagonal2.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.baeldung.pattern.hexagonal2.domain.model.User;
import com.baeldung.pattern.hexagonal2.port.primary.UserService;
import com.baeldung.pattern.hexagonal2.port.secondary.UserRepository;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User deleteUser(UUID id) {
        return userRepository.deleteUser(id);
    }
}
