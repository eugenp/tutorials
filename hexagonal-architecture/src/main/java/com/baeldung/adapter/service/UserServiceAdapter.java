package com.baeldung.adapter.service;

import com.baeldung.domain.User;
import com.baeldung.port.secondary.UserRepository;
import com.baeldung.port.service.UserServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceAdapter implements UserServicePort {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(String name, String username, String password, String email, String phone) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(Integer userId) {
        return userRepository.findById(userId);
    }
}
