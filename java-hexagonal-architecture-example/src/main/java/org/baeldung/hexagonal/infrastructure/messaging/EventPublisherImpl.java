package org.baeldung.hexagonal.infrastructure.messaging;

import org.baeldung.hexagonal.domain.event.Event;
import org.baeldung.hexagonal.domain.event.EventPublisher;

public class EventPublisherImpl implements EventPublisher {
    @Override
    public void publish(Event event) {
        System.out.println(event.getUsername() + " has been registered.");
    }
}
