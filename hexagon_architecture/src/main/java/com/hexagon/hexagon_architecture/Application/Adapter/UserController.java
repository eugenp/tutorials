package com.hexagon.hexagon_architecture.Application.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hexagon.hexagon_architecture.Domain.Model.User;
import com.hexagon.hexagon_architecture.Domain.Ports.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userservice;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers() {

        List<User> users = new ArrayList<>();
        users = userservice.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {

        Optional<User> user = userservice.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {

        if (user.getName() == null || user.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userservice.addUser(user);
            return ResponseEntity.ok().body(user);
        }

    }

}
