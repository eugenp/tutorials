package org.baeldung.hexagonal.domain.event;

public interface EventPublisher {
    public void publish(Event event);
}
