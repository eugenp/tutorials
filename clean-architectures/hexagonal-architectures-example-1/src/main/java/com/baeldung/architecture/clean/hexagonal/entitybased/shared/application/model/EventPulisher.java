package com.baeldung.architecture.clean.hexagonal.entitybased.shared.application.model;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * This Bean will be publishing events at the application layer, that's why it resides and resolves the publishing of the event on the same layer.
 */
@Component
@AllArgsConstructor
public class EventPulisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final Object object) {
        applicationEventPublisher.publishEvent(object);
    }
}
