package sample.cargotracker.registration.impl;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import com.lightbend.lagom.javadsl.pubsub.PubSubRef;
import com.lightbend.lagom.javadsl.pubsub.PubSubRegistry;
import com.lightbend.lagom.javadsl.pubsub.TopicId;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.cargotracker.registration.api.Cargo;
import sample.cargotracker.registration.api.RegistrationService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * Implementation of the RegistrationService.
 * Look at circuit breaker information: http://localhost:25003/_status/circuit-breaker/current
 */
public class RegistrationServiceImpl implements RegistrationService {

    private final PersistentEntityRegistry persistentEntityRegistry;
    private final PubSubRegistry topics;
    private final CassandraSession db;
    private final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    /**
     * Constructor with the relevant infrastructure elements injected.
     *
     * @param topics
     * @param persistentEntityRegistry
     * @param readSide
     * @param db
     */
    @Inject
    public RegistrationServiceImpl(PubSubRegistry topics, PersistentEntityRegistry persistentEntityRegistry, CassandraReadSide readSide,
                                   CassandraSession db) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        this.topics = topics;
        this.db = db;
        persistentEntityRegistry.register(CargoEntity.class);
        readSide.register(CargoEventProcessor.class);
    }

    /**
     * Register Cargo service call
     *
     * @return
     */
    @Override
    public ServiceCall<NotUsed, Cargo, Done> register() {
        return (id, request) -> {
            /* Publish received entity into topic named "Topic" */
            PubSubRef<Cargo> topic = topics.refFor(TopicId.of(Cargo.class, "topic"));
            topic.publish(request);
            log.info("Cargo ID: {}.", request.getId());
            /* Look up the Cargo entity for the given ID. */
            PersistentEntityRef<RegistrationCommand> ref =
                    persistentEntityRegistry.refFor(CargoEntity.class, request.getId());
            /* Tell the entity to use the Cargo information in the request. */
            return ref.ask(RegisterCargo.of(request));
        };
    }

    /**
     * Get live registrations service call
     *
     * @return
     */
    @Override
    public ServiceCall<NotUsed, NotUsed, Source<Cargo, ?>> getLiveRegistrations() {
        return (id, req) -> {
            PubSubRef<Cargo> topic = topics.refFor(TopicId.of(Cargo.class, "topic"));
            return CompletableFuture.completedFuture(topic.subscriber());
        };
    }


    /**
     * Get all persisted Cargo services call
     * Websockets capable
     *
     * @return
     * @deprecated
     */
    public ServiceCall<NotUsed, NotUsed, Source<Cargo, ?>> getAllRegistrationsOld() {
        log.info("Select all cargo .");
        return (id, req) -> {
            Source<Cargo, ?> result = db.select(
                    "SELECT cargoId, name, description, owner, destination FROM cargo;").map(row ->
                    Cargo.of(row.getString("cargoId"),
                            row.getString("name"),
                            row.getString("description"),
                            row.getString("owner"),
                            row.getString("destination")));
            return CompletableFuture.completedFuture(result);

        };
    }

    /**
     * Get all registered Cargo
     *
     * @return
     */
        @Override
        public ServiceCall<NotUsed, NotUsed, PSequence<Cargo>> getAllRegistrations() {
            return (userId, req) -> {
                CompletionStage<PSequence<Cargo>> result = db.selectAll("SELECT cargoid, name, description, owner, destination FROM cargo")
                        .thenApply(rows -> {
                            List<Cargo> cargos = rows.stream().map(row -> Cargo.of(row.getString("cargoid"),
                                    row.getString("name"),
                                    row.getString("description"),
                                    row.getString("owner"),
                                    row.getString("destination"))).collect(Collectors.toList());
                            return TreePVector.from(cargos);
                        });
                return result;
            };
        }

    public ServiceCall<String, NotUsed, Cargo> getRegistration() {

       //TODO Implement meaningful
        return null;
    }
}
