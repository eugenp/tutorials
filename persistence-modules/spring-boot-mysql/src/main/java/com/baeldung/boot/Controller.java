package com.baeldung.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public User get() {
        User user = new User();
        userRepository.save(user);
        return user;
    }

    @GetMapping("/find")
    public List<User> find() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
