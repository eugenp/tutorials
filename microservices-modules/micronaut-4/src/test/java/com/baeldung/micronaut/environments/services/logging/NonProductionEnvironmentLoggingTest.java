package com.baeldung.micronaut.environments.services.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "dev" })
public class NonProductionEnvironmentLoggingTest {

    @Inject
    LoggingService loggingService;

    @Test
    public void sendEvent_whenEnvIsNonProduction_usesDefaultConsoleLoggingService() {
        String devLog = loggingService.log("something");

        assertThat(devLog).isEqualTo("logging to console: [something]");
    }
}
