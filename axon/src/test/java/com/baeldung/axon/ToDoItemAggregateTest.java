package com.baeldung.axon;

import com.baeldung.axon.aggregates.ToDoItemAggregate;
import com.baeldung.axon.commands.CreateToDoItemCommand;
import com.baeldung.axon.commands.MarkCompletedCommand;
import com.baeldung.axon.events.ToDoItemCompletedEvent;
import com.baeldung.axon.events.ToDoItemCreatedEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class ToDoItemAggregateTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(ToDoItemAggregate.class);
    }

    @Test
    public void giveAggregateRoot_whenToDoItemCommand_thenShouldProduceToDoItemCreatedEvent() throws Exception {
        String eventToDoMessage = "buy something important";
        String toDoId = UUID.randomUUID().toString();
        fixture.given()
                .when(new CreateToDoItemCommand(toDoId, eventToDoMessage))
                .expectEvents(new ToDoItemCreatedEvent(toDoId, eventToDoMessage));
    }

    @Test
    public void givenTodoItemCreatedEvent_whenMarkCompletedCommand_thenShouldProduceToDoItemCompletedEvent() throws Exception {
        String toDoId = UUID.randomUUID().toString();

        fixture.given(new ToDoItemCreatedEvent(toDoId, "buy something more important"))
                .when(new MarkCompletedCommand(toDoId))
                .expectEvents(new ToDoItemCompletedEvent(toDoId));
    }
}