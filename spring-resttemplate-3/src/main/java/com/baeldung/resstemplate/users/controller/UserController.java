package com.baeldung.resstemplate.users.controller;

import com.baeldung.resttemplate.users.model.User;
import com.baeldung.resttemplate.users.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody List<User> getUsers() {
            List<User> userObjects = userService.getUsers();
            return userObjects;
        }

}
