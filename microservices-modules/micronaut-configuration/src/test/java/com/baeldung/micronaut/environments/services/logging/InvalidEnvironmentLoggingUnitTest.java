package com.baeldung.micronaut.environments.services.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.baeldung.micronaut.environments.ServerApplication;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;

public class InvalidEnvironmentLoggingUnitTest {

    @Test
    public void whenEnvironmentIsNotSet_thenLoggingServiceImplementationIsTheDefaultOne() {
        ApplicationContext applicationContext = Micronaut.run(ServerApplication.class);
        applicationContext.start();

        LoggingService loggingService = applicationContext.getBean(LoggingService.class);

        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactly("test");
        assertThat(loggingService).isNotNull();
        assertThat(loggingService).isExactlyInstanceOf(ConsoleLoggingServiceImpl.class);
    }
}
