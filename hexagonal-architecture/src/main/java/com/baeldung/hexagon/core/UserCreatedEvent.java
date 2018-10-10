package com.baeldung.hexagon.core;

/**
 * @author Ali Dehghani
 */
public class UserCreatedEvent {

    private final User user;

    public UserCreatedEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
