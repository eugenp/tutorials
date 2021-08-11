package com.baeldung.hexagonal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.service.UserService;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
    }
}
