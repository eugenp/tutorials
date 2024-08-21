package com.baeldung.micronaut.environments.services.eventsourcing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "dev" })
public class DevEnvironmentEventSourcingTest {

    @Inject
    EventSourcingService eventSourcingService;
    @Inject
    ApplicationContext applicationContext;

    @Test
    public void assertEnvironmentSet() {
        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactlyInAnyOrder("test", "dev");
    }

    @Test
    public void sendEvent_whenEnvIsDev_usesVoidEnvSourcingService() {
        String devEvent = eventSourcingService.sendEvent("something");

        assertThat(devEvent).isEqualTo("void service. [something] was not sent");
    }
}