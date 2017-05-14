package sample.cargotracker.registration.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import akka.Done;
import sample.cargotracker.registration.api.Cargo;

/**
 * This is an event sourced entity. It has a state, {@link CargoState}, which stores what the registered cargo caries.
 * <p/>
 * Event sourced entities are interacted with by sending them commands.  This entity supports one command,
 * a {@link RegisterCargo} command, which is used for cargo registration.
 * <p/>
 * Commands get translated to events, and it's the events that get persisted by the entity.  Each event
 * will have an event handler registered for it, and an event handler simply applies an event to the
 * current state.  This will be done when the event is first created, and it will also be done when the
 * entity is loaded from the database - each event will be replayed to recreate the state of the entity.
 * <p/>
 * This entity defines one event, the {@link CargoRegistered} event, which is emitted when a
 * {@link RegisterCargo} command is received.
 */
public class CargoEntity extends PersistentEntity<RegistrationCommand, RegistrationEvent, CargoState> {

    /**
     * An entity can define different behaviours for different states, but it will always start with an
     * initial behaviour.  This entity only has one behaviour.
     */
    @Override
    public Behavior initialBehavior(Optional<CargoState> snapshotState) {

        /**
         * Behaviour is defined using a behaviour builder. The behaviour builder starts with a state, if this
         * entity supports snapshotting (an optimisation that allows the state itself to be persisted to combine
         * many events into one), then the passed in snapshotState may have a value that can be used.
         *
         * Otherwise, the default state is to use a dummy Cargo with an id of empty string.
         */
        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
                CargoState.builder().cargo(
                        Cargo.builder()
                                .id("")
                                .description("")
                                .destination("")
                                .name("")
                                .owner("").build())
                        .timestamp(LocalDateTime.now()
                        ).build()));


        // Command handlers are invoked for incoming messages (commands).
        // A command handler must "return" the events to be persisted (if any).
        b.setCommandHandler(RegisterCargo.class, (cmd, ctx) -> {
            if (cmd.getCargo().getName() == null || cmd.getCargo().getName().equals("")) {
                ctx.invalidCommand("Name must be defined");
                return ctx.done();
            }

            final CargoRegistered cargoRegistered =
                    CargoRegistered.builder().cargo(cmd.getCargo()).id(entityId()).build();

            return ctx.thenPersist(cargoRegistered,  evt -> ctx.reply(Done.getInstance()));

        });
        /**
         * Event handler for the CargoRegistered event.
         */
        b.setEventHandler(CargoRegistered.class,
                // We simply update the current state to use the new cargo payload and update the timestamp
                evt -> state()
                        .withCargo(evt.getCargo())
                        .withTimestamp(LocalDateTime.now())
                        );


       // b.setReadOnlyCommandHandler()

        /**
         * We've defined all our behaviour, so build and return it.
         */
        return b.build();
    }
}

