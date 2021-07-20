package com.baeldung.hexagonalarchitecture.userside.rest;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.service.UserService;
import com.baeldung.hexagonalarchitecture.userside.request.UserCreateRequest;
import com.baeldung.hexagonalarchitecture.userside.response.UserActiveResponse;
import com.baeldung.hexagonalarchitecture.userside.response.UserCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserCreateResponse create(@RequestBody UserCreateRequest userCreateRequest) {
        UUID userId = userService.createUser(userCreateRequest);
        return new UserCreateResponse(userId);
    }

    @PutMapping("/active/{id}")
    public UserActiveResponse active(@PathVariable("id") UUID uuid) {
        User user = userService.activeUser(uuid);
        return new UserActiveResponse(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") UUID uuid) {
        userService.deleteUser(uuid);
    }
}
