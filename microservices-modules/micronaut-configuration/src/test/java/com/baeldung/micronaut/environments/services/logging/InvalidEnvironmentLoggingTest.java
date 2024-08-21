package com.baeldung.micronaut.environments.services.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.baeldung.micronaut.environments.ServerApplication;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;

public class InvalidEnvironmentLoggingTest {

    @Test
    public void log_whenEnvIsNone_usesDefaultLoggingService() {
        ApplicationContext applicationContext = Micronaut.run(ServerApplication.class);
        applicationContext.start();

        LoggingService loggingService = applicationContext.getBean(LoggingService.class);

        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactly("test");
        assertThat(loggingService).isNotNull();
        assertThat(loggingService).isExactlyInstanceOf(ConsoleLoggingServiceImpl.class);
    }
}
