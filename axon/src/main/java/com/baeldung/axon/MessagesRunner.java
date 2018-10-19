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
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import java.util.UUID;

public class MessagesRunner {

    public static void main(String[] args) {
        CommandBus commandBus = new SimpleCommandBus();

        CommandGateway commandGateway = new DefaultCommandGateway(commandBus);

        EventStore eventStore = new EmbeddedEventStore(new InMemoryEventStorageEngine());

        EventSourcingRepository<MessagesAggregate> repository =
                new EventSourcingRepository<>(MessagesAggregate.class, eventStore);


        AggregateAnnotationCommandHandler<MessagesAggregate> messagesAggregateAggregateAnnotationCommandHandler =
                new AggregateAnnotationCommandHandler<MessagesAggregate>(MessagesAggregate.class, repository);
        messagesAggregateAggregateAnnotationCommandHandler.subscribe(commandBus);

        final AnnotationEventListenerAdapter annotationEventListenerAdapter =
                new AnnotationEventListenerAdapter(new MessagesEventHandler());
        eventStore.subscribe(eventMessages -> eventMessages.forEach(e -> {
                    try {
                        annotationEventListenerAdapter.handle(e);
                    } catch (Exception e1) {
                        throw new RuntimeException(e1);

                    }
                }

        ));

        final String itemId = UUID.randomUUID().toString();
        commandGateway.send(new CreateMessageCommand(itemId, "Hello, how is your day? :-)"));
        commandGateway.send(new MarkReadMessageCommand(itemId));
    }
}