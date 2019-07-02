package com.baeldung.hexagone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private
    UserService service;

    @Override
    public List<User> getAllUsers() {
        if (null != service.getAllUsers()) {
            return service.getAllUsers();
        }
        return null;
    }

    @Override
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }
}
