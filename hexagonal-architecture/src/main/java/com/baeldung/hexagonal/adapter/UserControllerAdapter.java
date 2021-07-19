package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.port.UserUIPort;
import com.baeldung.hexagonal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerAdapter implements UserUIPort {

    @Autowired
    private UserService userService;

    @Override
    public String add(User user) {
        return userService.add(user);
    }

    @Override
    public User getDetail(String userId) {
        return userService.getDetail(userId);
    }
}
