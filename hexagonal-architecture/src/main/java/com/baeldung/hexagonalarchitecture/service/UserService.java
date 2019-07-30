package com.baeldung.hexagonalarchitecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.hexagonalarchitecture.model.User;
import com.baeldung.hexagonalarchitecture.repository.UserRepositoryPort;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepositoryPort userRepository;

    public void create(User user) {
        userRepository.create(user);
    }

    public User getUser(Long id) {
        return userRepository.getUser(id);
    }
}
