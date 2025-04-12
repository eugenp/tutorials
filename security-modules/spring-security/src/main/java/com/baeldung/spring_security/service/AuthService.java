package com.baeldung.spring_security.service;

import com.baeldung.spring_security.dto.request.RegisterRequestDto;
import com.baeldung.spring_security.dto.UserProfileDto;
import com.baeldung.spring_security.entity.User;
import com.baeldung.spring_security.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
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

    @Override
    public UserProfileDto profile(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        return user.map(value -> new UserProfileDto(value.getUsername(), value.getEmail(), value.getRole())).orElseThrow();
    }

    @Override
    public User getUser(Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        return user.orElse(null);
    }
}
