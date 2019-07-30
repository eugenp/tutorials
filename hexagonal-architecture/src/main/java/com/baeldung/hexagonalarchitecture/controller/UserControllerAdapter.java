package com.baeldung.hexagonalarchitecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalarchitecture.model.User;
import com.baeldung.hexagonalarchitecture.service.UserService;

@RestController
public class UserControllerAdapter implements UserUIPort {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.create(user);
    }

    @Override
    @GetMapping("/user")
    public User getUser(@RequestParam("id") Long id) {
        return userService.getUser(id);
    }

}
