package com.baeldung.micronaut.environments.services.eventsourcing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "dev" })
public class DevEnvironmentEventSourcingTest {

    @Inject
    EventSourcingService eventSourcingService;

    @Test
    public void sendEvent_whenEnvIsDev_usesVoidEnvSourcingService() {
        String devEvent = eventSourcingService.sendEvent("something");

        assertThat(devEvent).isEqualTo("void service. [something] was not sent");
    }
}