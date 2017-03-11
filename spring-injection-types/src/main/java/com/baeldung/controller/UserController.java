package com.baeldung.controller;

import com.baeldung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    @RequestMapping(value = "/greet/user" , method= RequestMethod.GET)
    public String greetUsers(){
        userService.greetUsers();
        return "Users are Greeted!";
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
