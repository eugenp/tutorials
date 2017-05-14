package sample.cargotracker.registration.impl;

import akka.Done;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import org.immutables.value.Value;
import sample.cargotracker.registration.api.Cargo;

/**
 * This interface defines all the commands that the Cargo entity supports.
 * <p>
 * By convention, the commands should be inner classes of the interface, which makes it simple to get a
 * complete picture of what commands an entity supports.
 */
public interface RegistrationCommand extends Jsonable {

    /**
     * A command to register cargo.
     * <p>
     * It has a reply type of {@link akka.Done}, which is sent back to the caller when all the events
     * emitted by this command are successfully persisted.
     */
    @Value.Immutable
    @ImmutableStyle
    @JsonDeserialize(as = RegisterCargo.class)
    public interface AbstractRegisterCargo extends RegistrationCommand, CompressedJsonable,
            PersistentEntity.ReplyType<Done> {

        @Value.Parameter
        Cargo getCargo();
    }

}
