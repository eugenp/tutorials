package com.baeldung.axon;

import com.baeldung.axon.aggregates.MessagesAggregate;
import com.baeldung.axon.commands.CreateMessageCommand;
import com.baeldung.axon.commands.MarkReadMessageCommand;
import com.baeldung.axon.events.MessageCreatedEvent;
import com.baeldung.axon.events.MessageReadEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class MessagesAggregateIntegrationTest {

    private FixtureConfiguration<MessagesAggregate> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<MessagesAggregate>(MessagesAggregate.class);

    }

    @Test
    public void giveAggregateRoot_whenCreateMessageCommand_thenShouldProduceMessageCreatedEvent() throws Exception {
        String eventText = "Hello, how is your day?";
        String id = UUID.randomUUID().toString();
        fixture.given()
                .when(new CreateMessageCommand(id, eventText))
                .expectEvents(new MessageCreatedEvent(id, eventText));
    }

    @Test
    public void givenMessageCreatedEvent_whenReadMessageCommand_thenShouldProduceMessageReadEvent() throws Exception {
        String id = UUID.randomUUID().toString();

        fixture.given(new MessageCreatedEvent(id, "Hello :-)"))
                .when(new MarkReadMessageCommand(id))
                .expectEvents(new MessageReadEvent(id));
    }
}