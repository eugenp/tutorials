package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model.events;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import org.springframework.context.ApplicationEvent;

public class UserRemovedEvent extends ApplicationEvent {

    private UserRemovedEvent(Object source) {
        super(source);
    }

    public static UserRemovedEvent of(User user) {
        return new UserRemovedEvent(user);
    }
}
