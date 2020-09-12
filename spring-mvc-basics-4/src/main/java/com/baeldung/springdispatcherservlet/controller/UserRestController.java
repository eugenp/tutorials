package com.baeldung.springdispatcherservlet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springdispatcherservlet.domain.User;
import com.baeldung.springdispatcherservlet.service.UserService;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public User fetchUserExample() {
        return userService.exampleUser();
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public User fetchUserByFirstName(@RequestParam(value = "firstName") String firstName) {
        return userService.fetchUserByFirstName(firstName);
    }
}