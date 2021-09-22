package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model;

import com.baeldung.architecture.clean.hexagonal.entitybased.shared.application.model.EventPulisher;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model.events.UserCreatedEvent;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.exception.DocumentIDInvalidException;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserCreateUseCase {
    private final IUserService userService;
    private final EventPulisher publisher;

    public User createUser(User user) throws DocumentIDInvalidException {
        User newUser = userService.createNewUser(user);
        newUser = userService.save(newUser);
        publisher.publishEvent(UserCreatedEvent.of(user));

        return newUser;
    }
}
