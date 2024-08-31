package com.baeldung.micronaut.environments.services.eventsourcing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "dev" })
public class DevEnvironmentEventSourcingUnitTest {

    @Inject
    EventSourcingService eventSourcingService;
    @Inject
    ApplicationContext applicationContext;
    @Property(name = "service.test.property")
    String testProperty;

    @Test
    public void whenEnvironmentIsSetToDev_thenActiveEnvironmentsAreTestAndDev() {
        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactlyInAnyOrder("test", "dev");
    }

    @Test
    public void whenEnvironmentIsSetToDev_thenTestPropertyGetsValueFromDev() {
        assertThat(testProperty).isEqualTo("something-in-dev");
    }

    @Test
    public void givenEnvironmentIsSetToDev_whenSendEvent_thenVoidServiceIsUsed() {
        String devEvent = eventSourcingService.sendEvent("something");

        assertThat(devEvent).isEqualTo("void service. [something] was not sent");
    }
}
