package com.baeldung.service;

import java.util.List;
import java.util.UUID;

import com.baeldung.domain.entity.user.User;
import com.baeldung.domain.exceptions.UserNotFoundException;
import com.baeldung.port.UserRepository;
import com.baeldung.port.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public User createUser(User user) {
        UUID userId = UUID.randomUUID();
        User newUser = User.builder()
                .id(userId)
                .name(user.getName())
                .email(user.getEmail())
                .build();
        return userRepository.createUser(newUser);
    }

    public boolean deleteUser(UUID userId) throws UserNotFoundException {
        User userToDelete = userRepository.findUserById(userId);
        return userRepository.deleteUser(userToDelete);
    }

    public List<User> listUsers() {
        return userRepository.listUsers();
    }
}
