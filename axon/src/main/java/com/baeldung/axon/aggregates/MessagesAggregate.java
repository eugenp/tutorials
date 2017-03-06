package com.baeldung.axon.aggregates;

import com.baeldung.axon.commands.CreateMessageCommand;
import com.baeldung.axon.commands.MarkReadMessageCommand;
import com.baeldung.axon.events.MessageReadEvent;
import com.baeldung.axon.events.MessageCreatedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class MessagesAggregate extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String id;

    public MessagesAggregate() {
    }

    @CommandHandler
    public MessagesAggregate(CreateMessageCommand command) {
        apply(new MessageCreatedEvent(command.getId(), command.getText()));
    }

    @EventHandler
    public void on(MessageCreatedEvent event) {
        this.id = event.getId();
    }

    @CommandHandler
    public void markCompleted(MarkReadMessageCommand command) {
        apply(new MessageReadEvent(id));
    }
}