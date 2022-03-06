package com.baeldung.controller;


import com.baeldung.domain.User;
import com.baeldung.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService _userService;

    @PostMapping()
    public  User createUser(@RequestBody User request) throws Exception {

        _userService.createUser(request);

        return  request;
    }

    @GetMapping()
    public List<User> getUsers() throws Exception {

        return  _userService.getUsers();
    }





}