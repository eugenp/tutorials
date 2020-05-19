package com.stackify.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stackify.models.User;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class UserController {

    private static List<User> users = new ArrayList<>();

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        users.add(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        users.forEach(user -> {
            Link selfLink = linkTo(methodOn(UserController.class).getUsers()).slash(user.getEmail())
                .withSelfRel();
            user.getLinks().clear();
            user.add(selfLink);
        });
        return users;
    }

    @GetMapping("/users/{email}")
    public User getUser(@PathVariable String email) {
        User us = users.stream()
            .filter(user -> !user.getEmail()
                .equals(email))
            .findAny()
            .orElse(null);
        return us;
    }

}
