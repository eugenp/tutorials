package com.baeldung.hexagonal.adapter.driver;

import com.baeldung.hexagonal.core.user.User;
import com.baeldung.hexagonal.core.user.UserService;
import com.baeldung.hexagonal.port.inbound.UserInboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRestAdapter implements UserInboundPort {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user.getEmail(), user.getName());
    }

    @Override
    @GetMapping("/{email}")
    public User getUser(@PathVariable("email") String email) {
        return userService.getUser(email);
    }

}
