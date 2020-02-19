package com.baeldung.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.domain.entity.user.User;
import com.baeldung.domain.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import com.baeldung.port.UserService;
import com.baeldung.service.UserServiceImpl;

@RestController
@RequestMapping(UserController.BASE_PATH)
@AllArgsConstructor
public class UserController {
    public static final String BASE_PATH = "users";
    private static final String RESOURCE_PATH = "{userId}";

    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @DeleteMapping(RESOURCE_PATH)
    public boolean deleteUser(@PathVariable UUID userId) throws UserNotFoundException {
        return userService.deleteUser(userId);
    }
}
