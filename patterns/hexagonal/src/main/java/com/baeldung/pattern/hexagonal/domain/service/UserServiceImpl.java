package com.baeldung.pattern.hexagonal.domain.service;

import com.baeldung.pattern.hexagonal.domain.model.User;
import com.baeldung.pattern.hexagonal.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
