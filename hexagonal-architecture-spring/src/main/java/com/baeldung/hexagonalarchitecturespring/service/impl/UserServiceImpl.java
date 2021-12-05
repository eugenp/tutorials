package com.baeldung.hexagonalarchitecturespring.service.impl;

import com.baeldung.hexagonalarchitecturespring.domain.User;
import com.baeldung.hexagonalarchitecturespring.exception.NotFoundException;
import com.baeldung.hexagonalarchitecturespring.repository.UserRepository;
import com.baeldung.hexagonalarchitecturespring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(String name, int age) {
        User user = new User(UUID.randomUUID(), name, age, false);
        return userRepository.create(user);
    }

    @Override
    public void enable(UUID id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void disable(UUID id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setActive(false);
        userRepository.save(user);
    }
}
