package sample.cargotracker.registration.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import sample.cargotracker.registration.api.Cargo;

/**
 * This interface defines all the events that the Cargo entity supports.
 *
 * By convention, the events should be inner classes of the interface, which makes it simple to get a
 * complete picture of what events an entity has.
 */
public interface RegistrationEvent extends Jsonable , AggregateEvent<RegistrationEvent> {

    /**
     * An event that represents a new cargo registration                                .
     */
    @Immutable
    @ImmutableStyle
    @JsonDeserialize(as = CargoRegistered.class)
    interface AbstractCargoRegistered extends RegistrationEvent {

        @Override
        default public AggregateEventTag<RegistrationEvent> aggregateTag() {
            return RegistrationEventTag.INSTANCE;
        }


        @Value.Parameter
        String getId();

        @Value.Parameter
        Cargo getCargo();
    }
}
