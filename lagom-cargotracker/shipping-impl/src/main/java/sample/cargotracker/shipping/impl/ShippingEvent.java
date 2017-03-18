package sample.cargotracker.shipping.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import java.util.Date;

/**
 * This interface defines all the events that the Shipping entity supports.
 *
 * By convention, the events should be inner classes of the interface, which makes it simple to get a
 * complete picture of what events an entity has.
 */
public interface ShippingEvent extends Jsonable {

    /**
     * An event that represents a new shipping itinerary.
     */
    @Immutable
    @ImmutableStyle
    @JsonDeserialize(as = ItineraryCreated.class)
    interface AbstractItineraryCreated extends ShippingEvent {

        @Value.Parameter
        String getId();

        @Value.Parameter
        String getCargoId();

        @Value.Parameter
        String getOrigin();

        @Value.Parameter
        String getDestination();
    }

    /**
     * An event that represents a leg being added.
     */
    @Immutable
    @ImmutableStyle
    @JsonDeserialize(as = LegAdded.class)
    interface AbstractLegAdded extends ShippingEvent {

        @Value.Parameter
        String getId();

        @Value.Parameter
        String getCargoId();

        @Value.Parameter
        String getLocation();

        @Value.Parameter
        Date getArrivalTime();

        @Value.Parameter
        Date getDepartureTime();
    }
}
