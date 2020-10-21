package com.baeldung.spring.data.snowflake.controller;

import com.baeldung.spring.data.snowflake.entity.User;
import com.baeldung.spring.data.snowflake.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userRepository.findByUserId(id);
    }

    @GetMapping("/user/all")
    public List<User> findAll() {
        return userRepository.findAllUsers();
    }

}
