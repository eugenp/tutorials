package sample.cargotracker.registration.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

/**
 * Register a common event tag
 */
public class RegistrationEventTag {

    public static final AggregateEventTag<RegistrationEvent> INSTANCE =
            AggregateEventTag.of(RegistrationEvent.class);

}
