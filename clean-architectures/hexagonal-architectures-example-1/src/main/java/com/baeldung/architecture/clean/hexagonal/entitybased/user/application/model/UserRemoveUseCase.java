package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model;

import com.baeldung.architecture.clean.hexagonal.entitybased.shared.application.model.EventPulisher;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model.events.UserRemovedEvent;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.service.UserService;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.exceptions.UserRepositoryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UserRemoveUseCase {
    private final UserService userService;
    private final EventPulisher publisher;

    public void removeUser(Long userId) {
        try {
            User userToRemove = userService.get(User.of(userId));
            userService.remove(userToRemove);
            publisher.publishEvent(UserRemovedEvent.of(userToRemove));
        } catch (UserRepositoryException e) {
            log.error(e.getMessage());
        }
    }
}
