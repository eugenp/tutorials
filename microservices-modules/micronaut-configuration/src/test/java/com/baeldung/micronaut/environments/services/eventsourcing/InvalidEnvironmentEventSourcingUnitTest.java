package com.baeldung.micronaut.environments.services.eventsourcing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com.baeldung.micronaut.environments.ServerApplication;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.exceptions.NoSuchBeanException;
import io.micronaut.runtime.Micronaut;

public class InvalidEnvironmentEventSourcingUnitTest {

    @Test
    public void whenEnvironmentIsNotSet_thenEventSourcingServiceBeanIsNotCreated() {
        ApplicationContext applicationContext = Micronaut.run(ServerApplication.class);
        applicationContext.start();

        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactly("test");
        assertThatThrownBy(() -> applicationContext.getBean(EventSourcingService.class)).isInstanceOf(NoSuchBeanException.class)
            .hasMessageContaining("None of the required environments [production] are active: [test]");
    }

    @Test
    public void whenEnvironmentIsNotSet_thenTestPropertyGetsValueFromDeductedEnvironment() {
        ApplicationContext applicationContext = Micronaut.run(ServerApplication.class);
        applicationContext.start();

        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactly("test");
        assertThat(applicationContext.getProperty("service.test.property", String.class)).isNotEmpty();
        assertThat(applicationContext.getProperty("service.test.property", String.class)
            .get()).isEqualTo("something-in-test");
    }
}
