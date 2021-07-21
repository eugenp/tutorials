package com.baeldung.hexagonalarchitecture.userside.cli;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.service.UserService;
import com.baeldung.hexagonalarchitecture.userside.request.UserCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CliUserController {

    private static final Logger LOG = LoggerFactory.getLogger(CliUserController.class);

    private UserService userService;

    @Autowired
    public CliUserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser() {
        UserCreateRequest userCreateRequest = new UserCreateRequest("user1", 10);
        UUID userId = userService.createUser(userCreateRequest);
        LOG.info("saved userId:" + userId);
        User user = userService.activeUser(userId);
        LOG.info(user.toString());
        LOG.info("Delete user with id " + user.getId());
        userService.deleteUser(userId);
    }
}
