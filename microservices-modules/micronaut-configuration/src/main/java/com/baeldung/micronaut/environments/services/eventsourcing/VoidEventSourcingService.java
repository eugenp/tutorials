package com.baeldung.micronaut.environments.services.eventsourcing;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import jakarta.inject.Singleton;

@Singleton
@Requires(env = Environment.DEVELOPMENT)
public class VoidEventSourcingService implements EventSourcingService {

    @Override
    public String sendEvent(String event) {
        return "void service. [" + event + "] was not sent";
    }
}
