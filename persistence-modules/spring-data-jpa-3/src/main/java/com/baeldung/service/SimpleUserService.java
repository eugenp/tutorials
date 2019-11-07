package com.baeldung.service;

import com.baeldung.model.User;
import com.baeldung.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findOne(String username) {
        return userRepository.findDetailedByUsername(username);
    }
}
