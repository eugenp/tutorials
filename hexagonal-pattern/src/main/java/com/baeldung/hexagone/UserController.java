package com.baeldung.hexagone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface UserController {
    @GetMapping("/getAllUsers")
    public List<User>getAllUsers();

    @PostMapping("/newUser")
    public User addUser(User user);
}
