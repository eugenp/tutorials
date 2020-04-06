package com.baeldung.javahexagonal.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.javahexagonal.core.domain.User;
import com.baeldung.javahexagonal.core.port.UserInputPort;

@RestController
@RequestMapping("/users")
public class UserDriverAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserDriverAdapter.class);

    @Autowired
    private UserInputPort userInputPort;

    @PostMapping(value = "/register")
    public User register(@RequestBody User user) throws Exception {
        logger.info("START: UserController.register");
        User registeredUser = userInputPort.registerUser(user);
        return registeredUser;
    }

    @PostMapping(value = "/update")
    public User updateUserById(@RequestBody User user) throws Exception {
        logger.info("Start: UserController.updateUserById");
        User updatedUser = userInputPort.updateUser(user);
        return updatedUser;
    }

    @GetMapping(value = "/fetch/by/email")
    public User getUserByEmail(@RequestParam("email") String email) {
        logger.info("Start: UserController.getUserByEmail");
        User user = userInputPort.getUserByEmail(email);
        return user;
    }

}
