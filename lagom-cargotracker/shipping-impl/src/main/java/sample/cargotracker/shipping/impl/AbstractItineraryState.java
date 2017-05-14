package sample.cargotracker.shipping.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.CompressedJsonable;
import java.util.List;
import java.time.LocalDateTime;
import org.immutables.value.Value;
import org.pcollections.PCollection;
import org.pcollections.PSequence;
import sample.cargotracker.shipping.api.Leg;

/**
 * The state for the itinerary entity.
 */
@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = ItineraryState.class)
public abstract class AbstractItineraryState implements CompressedJsonable {

    /**
     * The itinerary id.
     */
    @Value.Parameter
    public abstract String getId();

    /**
     * The cargo id.
     */
    @Value.Parameter
    public abstract String getCargoId();

    /**
     * The cargo origin.
     */
    @Value.Parameter
    public abstract String getOrigin();

    /**
     * The cargo destination.
     */
    @Value.Parameter
    public abstract String getDestination();

    /**
     * The legs for the itinerary.
     */
    @Value.Parameter
    public abstract PSequence<Leg> getLegs();

    /**
     * When the greeting last changed.
     */
    @Value.Parameter
    public abstract LocalDateTime getTimestamp();
}
