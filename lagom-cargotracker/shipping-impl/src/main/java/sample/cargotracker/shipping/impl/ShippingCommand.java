package sample.cargotracker.shipping.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import java.util.Optional;
import org.immutables.value.Value;

import sample.cargotracker.shipping.api.Itinerary;

import akka.Done;
import sample.cargotracker.shipping.api.Leg;

/**
 * This interface defines all the commands that the Itinerary entity supports.
 *
 * By convention, the commands should be inner classes of the interface, which makes it simple to get a
 * complete picture of what commands an entity supports.
 */
public interface ShippingCommand extends Jsonable {

    /**
     * A command to create an itinerary.
     *
     * It has a reply type of {@link akka.Done}, which is sent back to the caller when all the events
     * emitted by this command are successfully persisted.
     */
    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize(as = CreateItinerary.class)
    public interface AbstractCreateItinerary extends ShippingCommand, CompressedJsonable,
            PersistentEntity.ReplyType<Done> {

        @Value.Parameter
        Itinerary getItinerary();
    }

    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize(as = AddLeg.class)
    public interface AbstractAddLeg extends ShippingCommand, CompressedJsonable,
            PersistentEntity.ReplyType<Done> {

        @Value.Parameter
        Leg getLeg();
    }
}
