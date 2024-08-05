package com.baeldung.quarkus.todos.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.baeldung.quarkus.todos.domain.Todo;
import com.baeldung.quarkus.todos.domain.TodoEvents.TodoCreated;

@ApplicationScoped
public class TodoCreatedKafkaProducer {

    @Inject
    @Channel("todos")
    Emitter<Todo> emitter;

    public void produceTodoCreatedMessage(@Observes TodoCreated event) {
        emitter.send(event.getTodo());
    }

}
