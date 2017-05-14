package sample.cargotracker.registration.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = Cargo.class)
public interface AbstractCargo {

    @Value.Parameter
    String getId();

    @Value.Parameter
    String getName();

    @Value.Parameter
    String getDescription();

    @Value.Parameter
    String getOwner();

    @Value.Parameter
    String getDestination();
}
