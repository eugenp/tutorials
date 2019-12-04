package com.baeldung.api;


import com.baeldung.inbound.UserService;
import com.baeldung.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    public User createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto.toUser());
    }
}