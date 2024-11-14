package com.baeldung.userservice.controller;

import com.baeldung.userservice.model.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final Map<Long, User> userMap = new HashMap<>();

    @GetMapping(path = "/user/{id}")
    public User getUser(@PathVariable("id") long userId){
        LOGGER.info("Getting user Details for user Id {}", userId);
        return userMap.get(userId);
    }

    @PostConstruct
    private void setupRepo() {
        User user1 = getUser(100001, "user1");
        userMap.put(100001L, user1);

        User user2 = getUser(100002, "user2");
        userMap.put(100002L, user2);

        User user3 = getUser(100003, "user3");
        userMap.put(100003L, user3);

        User user4 = getUser(100004, "user4");
        userMap.put(100004L, user4);
    }

    private static User getUser(int id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
}
