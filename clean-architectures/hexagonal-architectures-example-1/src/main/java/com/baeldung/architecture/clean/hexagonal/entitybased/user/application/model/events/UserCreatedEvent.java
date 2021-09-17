package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model.events;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {

    private UserCreatedEvent(Object source) {
        super(source);
    }

    public static UserCreatedEvent of(User user) {
        return new UserCreatedEvent(user);
    }
}
