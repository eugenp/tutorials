package com.baeldung.nullfileds;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserEchoController.USERS)
public class UserEchoController {

    static final String USERS = "/users";

    @PostMapping
    public User echoUser(@RequestBody User user) {
        return user;
    }
}
