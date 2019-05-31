package com.baeldung.hexagonalarch.adapter;

import com.baeldung.hexagonalarch.model.User;
import com.baeldung.hexagonalarch.port.UserPort;
import com.baeldung.hexagonalarch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/")
public class UserController implements UserPort {

    @Autowired
    UserService userService;

    @Override
    public void create(User user) {
        userService.create(user);
    }

    @Override
    public User getUser(Integer userid) {
        return userService.getUser(userid);
    }
}
