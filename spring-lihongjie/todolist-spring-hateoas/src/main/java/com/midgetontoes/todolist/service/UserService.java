package com.midgetontoes.todolist.service;

import com.midgetontoes.todolist.jpa.UserRepository;
import com.midgetontoes.todolist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
