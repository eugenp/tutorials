package sample.cargotracker.shipping.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;

import java.util.Date;

import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = Leg.class)
public interface AbstractLeg {

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
