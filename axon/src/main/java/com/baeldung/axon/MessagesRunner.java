package com.baeldung.axon;

import com.baeldung.axon.aggregates.MessagesAggregate;
import com.baeldung.axon.commands.CreateMessageCommand;
import com.baeldung.axon.commands.MarkReadMessageCommand;
import com.baeldung.axon.eventhandlers.MessagesEventHandler;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import java.io.File;
import java.util.UUID;

public class MessagesRunner {

    public static void main(String[] args) {
        CommandBus commandBus = new SimpleCommandBus();

        CommandGateway commandGateway = new DefaultCommandGateway(commandBus);

        EventStore eventStore = new EmbeddedEventStore(new InMemoryEventStorageEngine());
        EventBus eventBus = new SimpleEventBus();

        EventSourcingRepository<MessagesAggregate> repository =
                new EventSourcingRepository<MessagesAggregate>(MessagesAggregate.class, eventStore);


        AggregateAnnotationCommandHandler<MessagesAggregate> messagesAggregateAggregateAnnotationCommandHandler =
                new AggregateAnnotationCommandHandler<MessagesAggregate>(MessagesAggregate.class, repository);
        messagesAggregateAggregateAnnotationCommandHandler.subscribe(commandBus);

        AnnotationEventListenerAdapter annotationEventListenerAdapter = new AnnotationEventListenerAdapter(new MessagesEventHandler());

        final String itemId = UUID.randomUUID().toString();
        commandGateway.send(new CreateMessageCommand(itemId, "Hello, how is your day? :-)"));
        commandGateway.send(new MarkReadMessageCommand(itemId));
    }
}