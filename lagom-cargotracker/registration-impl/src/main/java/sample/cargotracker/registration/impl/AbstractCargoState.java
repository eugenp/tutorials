package sample.cargotracker.registration.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.serialization.CompressedJsonable;
import java.time.LocalDateTime;
import java.util.Optional;

import org.immutables.value.Value;
import sample.cargotracker.registration.api.Cargo;

/**
 * The state for the cargo entity.
 */
@Value.Immutable
@ImmutableStyle
@JsonDeserialize(as = CargoState.class)
public interface AbstractCargoState  {


    @Value.Parameter
     Cargo getCargo();

    @Value.Parameter
     LocalDateTime getTimestamp();
}
