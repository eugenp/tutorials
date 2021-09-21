package com.baeldung.hexagonal.adapter;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.model.User;
import com.baeldung.hexagonal.domain.port.UserRepository;
import com.baeldung.hexagonal.domain.port.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceAdapter implements UserService {
    final private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
}
