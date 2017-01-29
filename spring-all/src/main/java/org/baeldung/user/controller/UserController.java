package org.baeldung.user.controller;

import org.baeldung.user.model.MyUser;
import org.baeldung.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    /**
     * Controller method to fetch user object based on user name
     *
     * @param userName
     * @return
     */
    @RequestMapping("/user")
    @ResponseBody
    public MyUser getUser(@RequestParam String userName) {
        return userService.getUserByUsername(userName);
    }
}
