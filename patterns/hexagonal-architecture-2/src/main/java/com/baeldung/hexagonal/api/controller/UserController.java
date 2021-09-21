package com.baeldung.hexagonal.api.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.hexagonal.domain.model.User;
import com.baeldung.hexagonal.domain.port.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUserById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user"));
    }
}
