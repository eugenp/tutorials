package com.baeldung.web.controller;

import com.baeldung.model.User;
import com.baeldung.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    @ResponseBody
    public User fetchUserExample() {
        return userService.exampleUser();
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ResponseBody
    public User fetchUserByFirstName(@RequestParam(value = "firstName") String firstName) {
        return userService.fetchUserByFirstName(firstName);
    }
}