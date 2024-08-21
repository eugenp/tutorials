package com.baeldung.micronaut.environments.services.eventsourcing;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Singleton
@Requires(env = "production")
public class KafkaEventSourcingService implements EventSourcingService {

    @Override
    public String sendEvent(String event) {
        return "using kafka to send message: [" + event + "]";
    }
}
