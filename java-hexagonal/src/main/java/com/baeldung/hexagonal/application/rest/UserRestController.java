package com.baeldung.hexagonal.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.entity.User;
import com.baeldung.hexagonal.domain.service.UserService;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @GetMapping("rest/users")
    public User getUserInfo() {
        return userService.getUser();
    }

}
