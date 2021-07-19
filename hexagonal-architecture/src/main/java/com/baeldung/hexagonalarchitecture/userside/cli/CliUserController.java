package com.baeldung.hexagonalarchitecture.userside.cli;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.service.UserService;
import com.baeldung.hexagonalarchitecture.userside.request.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CliUserController {
    private UserService userService;

    @Autowired
    public CliUserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser() {
        UserCreateRequest userCreateRequest = new UserCreateRequest("user1", 10);
        UserCreateRequest userCreateRequest2 = new UserCreateRequest("user2", 100);
        UUID userId = userService.createUser(userCreateRequest);
        System.out.println(userId);
        User user = userService.activeUser(userId);
        System.out.println(user.getName());
    }
}
