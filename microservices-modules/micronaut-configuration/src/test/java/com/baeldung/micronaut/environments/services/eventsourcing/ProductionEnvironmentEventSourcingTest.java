package com.baeldung.micronaut.environments.services.eventsourcing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "production" })
public class ProductionEnvironmentEventSourcingTest {

    @Inject
    EventSourcingService eventSourcingService;
    @Inject
    ApplicationContext applicationContext;

    @Test
    public void assertEnvironmentSet() {
        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactlyInAnyOrder("test", "production");
    }

    @Test
    public void sendEvent_whenEnvIsProduction_usesKafkaEventSourcingService() {
        String devEvent = eventSourcingService.sendEvent("something");

        assertThat(devEvent).isEqualTo("using kafka to send message: [something]");
    }
}
