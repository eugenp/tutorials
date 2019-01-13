package com.baeldung.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.domain.User;
import com.baeldung.hexagonal.port.UserUIPort;
import com.baeldung.service.UserService;

@RestController
@RequestMapping("/user/")
public class UserControllerAdapter implements UserUIPort {

    @Autowired
    private UserService userService;

    @Override
    public void register(@RequestBody User request) {
        userService.register(request.getName());
    }

    @Override
    public User view(@PathVariable Integer id) {
        User employee = userService.view(id);
        return employee;
    }

}
