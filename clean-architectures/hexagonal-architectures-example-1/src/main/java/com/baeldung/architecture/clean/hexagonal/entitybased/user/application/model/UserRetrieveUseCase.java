package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRetrieveUseCase {
    private final UserService userService;

    public User retrieveUser(Long userId) {
        return userService.get(User.of(userId));
    }
}
