package sample.cargotracker.shipping.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

import java.util.List;

import org.immutables.value.Value;

import org.pcollections.PSequence;
import sample.cargotracker.shipping.api.Leg;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = Itinerary.class)
public interface AbstractItinerary {

    @Value.Parameter
    String getId();

    @Value.Parameter
    String getCargoId();

    @Value.Parameter
    String getOrigin();

    @Value.Parameter
    String getDestination();

    @Value.Parameter
    PSequence<Leg> getLegs();
}
