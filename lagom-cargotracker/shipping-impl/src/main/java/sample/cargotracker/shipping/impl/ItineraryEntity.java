package sample.cargotracker.shipping.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import java.time.LocalDateTime;
import java.util.Optional;

import akka.Done;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;
import sample.cargotracker.shipping.api.Leg;

public class ItineraryEntity extends PersistentEntity<ShippingCommand, ShippingEvent, ItineraryState> {
  /**
   * An entity can define different behaviours for different states, but it will always start with an
   * initial behaviour.  This entity only has one behaviour.
   */
  @Override
  public Behavior initialBehavior(Optional<ItineraryState> snapshotState) {

    /*
     * Behaviour is defined using a behaviour builder. The behaviour builder starts with a state, if this
     * entity supports snapshotting (an optimisation that allows the state itself to be persisted to combine
     * many events into one), then the passed in snapshotState may have a value that can be used.
     *
     * Otherwise, the default state is to use a dummy Cargo with and id of 0L..
     */
    BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
        ItineraryState.builder()
                .id("")
                .cargoId("")
                .origin("")
                .destination("")
                .legs(TreePVector.empty())
                .timestamp(LocalDateTime.now()).build()));

    /*
     * Command handler for the create itinerary command.
     */
    b.setCommandHandler(CreateItinerary.class,
        (cmd, ctx) ->
            // In response to this command, we want to first persist it as a itinerary created event
            ctx.thenPersist(
                ItineraryCreated.of(
                        cmd.getItinerary().getId(),
                        cmd.getItinerary().getCargoId(),
                        cmd.getItinerary().getOrigin(),
                        cmd.getItinerary().getDestination()),
                // Then once the event is successfully persisted, we respond with done.
                evt -> ctx.reply(Done.getInstance())
            )
    );

    /*
     * Command handler for the create itinerary command.
     */
    b.setCommandHandler(AddLeg.class,
            (cmd, ctx) ->
                    // In response to this command, we want to first persist it as a itinerary created event
                    ctx.thenPersist(
                            LegAdded.of(
                                    cmd.getLeg().getId(),
                                    cmd.getLeg().getCargoId(),
                                    cmd.getLeg().getLocation(),
                                    cmd.getLeg().getArrivalTime(),
                                    cmd.getLeg().getDepartureTime()),
                            // Then once the event is successfully persisted, we respond with done.
                            evt -> ctx.reply(Done.getInstance())
                    )
    );

    /*
     * Event handler for the itinerary created event.
     */
    b.setEventHandler(LegAdded.class,
            evt -> {
              final Leg leg = Leg.builder()
                      .id(evt.getId())
                      .cargoId(evt.getCargoId())
                      .location(evt.getLocation())
                      .arrivalTime(evt.getArrivalTime())
                      .departureTime(evt.getDepartureTime())
                      .build();
              return state().withLegs(state().getLegs().plus(leg));
            }
    );

    /*
     * Event handler for the itinerary created event.
     */
    b.setEventHandler(ItineraryCreated.class,
        // We simply update the current state to use the greeting message from the event.
        evt -> state()
                .withId(evt.getId())
                .withCargoId(evt.getCargoId())
                .withOrigin(evt.getOrigin())
                .withDestination(evt.getDestination())
                .withTimestamp(LocalDateTime.now())
    );

    /*
     * We've defined all our behaviour, so build and return it.
     */
    return b.build();
  }


}
