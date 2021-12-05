package com.baeldung.hexagonalarchitecturespring.controller;

import com.baeldung.hexagonalarchitecturespring.domain.User;
import com.baeldung.hexagonalarchitecturespring.domain.UserInformation;
import com.baeldung.hexagonalarchitecturespring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User insert(
            @RequestBody UserInformation userInformation
    ) {
        return userService.create(userInformation.getName(), userInformation.getAge());
    }
}
