package com.baeldung.micronaut.environments.services.eventsourcing;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com.baeldung.micronaut.environments.ServerApplication;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.exceptions.NoSuchBeanException;
import io.micronaut.runtime.Micronaut;

public class InvalidEnvironmentEventSourcingTest {

    @Test
    public void sendEvent_whenEnvIsInvalid_throwsBeanException() {
        ApplicationContext applicationContext = Micronaut.run(ServerApplication.class);
        applicationContext.start();

        assertThatThrownBy(() -> applicationContext.getBean(EventSourcingService.class)).isInstanceOf(NoSuchBeanException.class)
            .hasMessageContaining("None of the required environments [production] are active: [test]");

        // remove it later. keep it to mention it in the article as the exception thrown if a default impl is missing
        //        assertThatThrownBy(() -> eventSourcingService.sendEvent("something"))
        //            .isInstanceOf(DependencyInjectionException.class)
        //            .hasMessage("No bean of type [com.baeldung.micronaut.environments.services.eventsourcing.EventSourcingService] exists.");
    }
}
