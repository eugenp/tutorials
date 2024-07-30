package com.baeldung.quarkus.todos.config;

import static org.citrusframework.kafka.endpoint.builder.KafkaEndpoints.kafka;

import org.citrusframework.kafka.endpoint.KafkaEndpoint;
import org.citrusframework.spi.BindToRegistry;

public class KafkaCitrusConfig {

    public static final String TODOS_EVENTS_TOPIC = "todosEvents";

    @BindToRegistry(name = TODOS_EVENTS_TOPIC)
    public KafkaEndpoint todosEvents() {
        return kafka().asynchronous()
            .topic("todo-events")
            .build();
    }

}
