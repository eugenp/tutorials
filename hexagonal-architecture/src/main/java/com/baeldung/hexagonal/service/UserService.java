package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepositoryPort userRepository;

    public String add(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);

        userRepository.add(user);

        return id;
    }

    public User getDetail(String userId) {
        return userRepository.getDetail(userId);
    }
}
