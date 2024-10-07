package com.baeldung.micronaut.environments.services.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "production" })
public class ProductionEnvironmentLoggingUnitTest {

    @Inject
    LoggingService loggingService;
    @Inject
    ApplicationContext applicationContext;

    @Test
    public void whenEnvironmentIsSetToProduction_thenActiveEnvironmentsAreTestAndProduction() {
        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactlyInAnyOrder("test", "production");
    }

    @Test
    public void givenEnvironmentIsSetToProduction_whenLog_thenOverridesDefaultAndUsesFileLoggingService() {
        String prodLog = loggingService.log("something");

        assertThat(prodLog).isEqualTo("logging to some file: [something]");
    }
}
