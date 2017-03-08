package com.baeldung.axon;

import com.baeldung.axon.aggregates.MessagesAggregate;
import com.baeldung.axon.commands.CreateMessageCommand;
import com.baeldung.axon.commands.MarkReadMessageCommand;
import com.baeldung.axon.eventhandlers.MessagesEventHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;

import java.io.File;
import java.util.UUID;

public class MessagesRunner {

    public static void main(String[] args) {
        CommandBus commandBus = new SimpleCommandBus();

        CommandGateway commandGateway = new DefaultCommandGateway(commandBus);

        EventStore eventStore = new FileSystemEventStore(new SimpleEventFileResolver(new File("./events")));

        EventBus eventBus = new SimpleEventBus();

        EventSourcingRepository<MessagesAggregate> repository =
                new EventSourcingRepository<MessagesAggregate>(MessagesAggregate.class, eventStore);
        repository.setEventBus(eventBus);

        AggregateAnnotationCommandHandler.subscribe(MessagesAggregate.class, repository, commandBus);

        AnnotationEventListenerAdapter.subscribe(new MessagesEventHandler(), eventBus);

        final String itemId = UUID.randomUUID().toString();
        commandGateway.send(new CreateMessageCommand(itemId, "Hello, how is your day? :-)"));
        commandGateway.send(new MarkReadMessageCommand(itemId));
    }
}