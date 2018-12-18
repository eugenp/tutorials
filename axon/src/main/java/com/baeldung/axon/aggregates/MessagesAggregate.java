package com.baeldung.axon.aggregates;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.baeldung.axon.commands.CreateMessageCommand;
import com.baeldung.axon.commands.MarkReadMessageCommand;
import com.baeldung.axon.events.MessageCreatedEvent;
import com.baeldung.axon.events.MessageReadEvent;

@Aggregate
public class MessagesAggregate {

    @AggregateIdentifier
    private String id;

    public MessagesAggregate() {
    }

    @CommandHandler
    public MessagesAggregate(CreateMessageCommand command) {
        apply(new MessageCreatedEvent(command.getId(), command.getText()));
    }

    @CommandHandler
    public void handle(MarkReadMessageCommand command) {
        apply(new MessageReadEvent(id));
    }

    @EventSourcingHandler
    public void on(MessageCreatedEvent event) {
        this.id = event.getId();
    }
}