package com.baeldung.detachentity.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.baeldung.detachentity.domain.User;
import com.baeldung.detachentity.repository.UserRepository;

@Service
public class UserDataService {
    private final UserRepository userRepository;

    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setActivated(false);

        User savedUser = userRepository.save(user);
        userRepository.detach(savedUser);

        return savedUser;
    }
    
    @Transactional
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found for Id" + id));

        user.setActivated(true);
        return user;
    }
}
