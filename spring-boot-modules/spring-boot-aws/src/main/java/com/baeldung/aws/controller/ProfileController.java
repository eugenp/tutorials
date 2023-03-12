package com.baeldung.aws.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.aws.data.User;

@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUser() {
        return List.of(new User("Jonh", "Doe", "jonh.doe@baeldung.com"), new User("Jonh", "Doe", "jonh.doe-2@baeldung.com"));
    }

}
