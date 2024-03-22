package com.baeldung.spring.kafka.startstopconsumer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserEventStore {

    private final List<UserEvent> userEvents = new ArrayList<>();

    public void addUserEvent(UserEvent userEvent) {
        userEvents.add(userEvent);
    }

    public List<UserEvent> getUserEvents() {
        return userEvents;
    }

    public void clearUserEvents() {
        this.userEvents.clear();
    }
}
