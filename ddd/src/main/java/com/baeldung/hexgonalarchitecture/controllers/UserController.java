package com.baeldung.hexgonalarchitecture.controllers;

import com.baeldung.hexgonalarchitecture.entity.User;
import com.baeldung.hexgonalarchitecture.ports.definition.UserInputPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserInputPort userInputPort;

    public UserController(UserInputPort userInputPort) {
        this.userInputPort = userInputPort;
    }

    @PostMapping("/save")
    private ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userInputPort.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
