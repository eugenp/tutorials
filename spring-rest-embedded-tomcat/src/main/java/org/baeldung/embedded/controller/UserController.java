package org.baeldung.embedded.controller;

import org.baeldung.embedded.domain.User;
import org.baeldung.embedded.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String welcome() {
        return "Hello World!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userName}")
    @ResponseBody
    public User user(@PathVariable String userName) {
        return this.userService.getUser(userName);
    }
}
