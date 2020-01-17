package com.baeldung.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.service.UserServicePort;

@RestController
@RequestMapping("/api/user")
public class UserControllerAdapter {

    @Autowired
    UserServicePort userServicePort;

    public void createUser(@RequestBody User userObj) {
        userServicePort.createUser(userObj.getUserName());
    }

    public User getUser(@PathVariable Long userId) {

        User user = userServicePort.getUser(userId);
        return user;
    }
}
