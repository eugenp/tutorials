package com.baeldung.springsecurity.service;

import com.baeldung.springsecurity.dto.request.RegisterRequestDto;
import com.baeldung.springsecurity.dto.UserProfileDto;
import com.baeldung.springsecurity.entity.User;
import com.baeldung.springsecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
        return "User registered successfully";
    }

    public UserProfileDto profile(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> new UserProfileDto(value.getUsername(), value.getEmail(), value.getRole())).orElseThrow();
    }

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
