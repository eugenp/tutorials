package com.baeldung.axon;

import com.baeldung.axon.aggregates.MessagesAggregate;
import com.baeldung.axon.commands.CreateMessageCommand;
import com.baeldung.axon.commands.MarkReadMessageCommand;
import com.baeldung.axon.events.MessageReadEvent;
import com.baeldung.axon.events.MessageCreatedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class MessagesAggregateTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(MessagesAggregate.class);
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