package com.baeldung.quarkus.todos.messaging;

import com.baeldung.quarkus.todos.domain.Todo;
import com.baeldung.quarkus.todos.domain.TodoEvents.TodoCreated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class TodoCreatedKafkaProducer {

    @Inject
    @Channel("todos")
    Emitter<Todo> emitter;

    public void produceTodoCreatedMessage(@Observes TodoCreated event) {
        emitter.send(event.getTodo());
    }

}
