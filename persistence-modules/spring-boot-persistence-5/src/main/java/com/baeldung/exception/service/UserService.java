package com.baeldung.exception.service;

import com.baeldung.exception.UserNotFoundException;
import com.baeldung.exception.entity.User;
import com.baeldung.exception.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Optional-based API
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Business-level exception handling
    public User findUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
          .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }

    public User findUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
          .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}