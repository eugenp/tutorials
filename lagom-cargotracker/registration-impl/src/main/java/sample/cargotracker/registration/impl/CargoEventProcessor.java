package sample.cargotracker.registration.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import com.datastax.driver.core.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

/**
 * Transform the Persistent Entity events into Cassandra database
 * tables and records that can be queried from a service.
 */
public class CargoEventProcessor extends CassandraReadSideProcessor<RegistrationEvent> {

    private final Logger log = LoggerFactory.getLogger(CargoEventProcessor.class);

    @Override
    public AggregateEventTag<RegistrationEvent> aggregateTag() {
        return RegistrationEventTag.INSTANCE;
    }


    private PreparedStatement writeCargo = null; // initialized in prepare
    private PreparedStatement writeOffset = null; // initialized in prepare

    private void setWriteCargo(PreparedStatement writeCargo) {
        this.writeCargo = writeCargo;
    }

    private void setWriteOffset(PreparedStatement writeOffset) {
        this.writeOffset = writeOffset;
    }

    /**
     * Prepare read-side table and statements
     *
     * @param session
     * @return
     */
    @Override
    public CompletionStage<Optional<UUID>> prepare(CassandraSession session) {
        // @formatter:off
        return
                prepareCreateTables(session).thenCompose(a ->
                        prepareWriteCargo(session).thenCompose(b ->
                                prepareWriteOffset(session).thenCompose(c ->
                                        selectOffset(session))));
        // @formatter:on
    }

    /**
     * prepare read-side tables
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareCreateTables(CassandraSession session) {
        // @formatter:off
        return session.executeCreateTable(
                "CREATE TABLE IF NOT EXISTS cargo ("
                        + "cargoId text, name text, description text, owner text, destination text,"
                        + "PRIMARY KEY (cargoId, destination))")
                .thenCompose(a -> session.executeCreateTable(
                        "CREATE TABLE IF NOT EXISTS cargo_offset ("
                                + "partition int, offset timeuuid, "
                                + "PRIMARY KEY (partition))"));
        // @formatter:on
    }

    /**
     * prepared statement for writing a Cargo object
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareWriteCargo(CassandraSession session) {
        return session.prepare("INSERT INTO cargo (cargoId, name, description, owner,destination) VALUES (?, ?,?,?,?)").thenApply(ps -> {
            setWriteCargo(ps);
            return Done.getInstance();
        });
    }

    /**
     * Prepared statement for the persistence offset
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareWriteOffset(CassandraSession session) {
        return session.prepare("INSERT INTO cargo_offset (partition, offset) VALUES (1, ?)").thenApply(ps -> {
            setWriteOffset(ps);
            return Done.getInstance();
        });
    }

    /**
     * Find persistence offset
     *
     * @param session
     * @return
     */
    private CompletionStage<Optional<UUID>> selectOffset(CassandraSession session) {
        return session.selectOne("SELECT offset FROM cargo_offset")
                .thenApply(
                        optionalRow -> optionalRow.map(r -> r.getUUID("offset")));
    }

    /**
     * Bind the read side persistence to the CargoRegistered event
     *
     * @param builder
     * @return
     */
    @Override
    public EventHandlers defineEventHandlers(EventHandlersBuilder builder) {
        builder.setEventHandler(CargoRegistered.class, this::processCargoRegistered);
        return builder.build();
    }

    /**
     * Write a persistent event into the read-side optimized database.
     *
     * @param @link{CargoRegistered}
     * @param offset
     * @return
     */
    private CompletionStage<List<BoundStatement>> processCargoRegistered(CargoRegistered event, UUID offset) {
        BoundStatement bindWriteCargo = writeCargo.bind();
        bindWriteCargo.setString("cargoId", event.getCargo().getId());
        bindWriteCargo.setString("name", event.getCargo().getName());
        bindWriteCargo.setString("description", event.getCargo().getDescription());
        bindWriteCargo.setString("owner", event.getCargo().getOwner());
        bindWriteCargo.setString("destination", event.getCargo().getDestination());
        BoundStatement bindWriteOffset = writeOffset.bind(offset);
        log.info("Persisted {}", event.getCargo().getId());
        return completedStatements(Arrays.asList(bindWriteCargo, bindWriteOffset));
    }

}
