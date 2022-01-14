package com.baeldung.hexagonal.controllers;

import com.baeldung.hexagonal.domain.entities.User;
import com.baeldung.hexagonal.domain.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.getUser(id).orElse(null), HttpStatus.OK);
    }

    @GetMapping("/{id}/email")
    public ResponseEntity<String> getEmail(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.getEmail(id), HttpStatus.OK);
    }

}
