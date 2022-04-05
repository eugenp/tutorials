package com.baeldung.annotations.conditional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

public class DevEnvLoggingConfigurationUnitTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    public void whenDevEnvEnabled_thenDevEnvLoggingConfigurationAndLoggingServiceShouldBeCreated() {
        System.setProperty("env", "dev");

        contextRunner
          .withUserConfiguration(ConditionalTestConfiguration.class)
          .run(context ->
            Assertions.assertNotNull(
              context.getBean(DevEnvLoggingConfiguration.class)
            )
          );
        contextRunner
          .withUserConfiguration(ConditionalTestConfiguration.class)
          .run(context ->
            Assertions.assertNotNull(
              context.getBean(LoggingService.class)
            )
          );
    }

    @Test
    public void whenDevEnvNotEnabled_thenDevEnvLoggingConfigurationAndLoggingServiceShouldNotBeCreated() {
        System.setProperty("env", "not-dev");

        contextRunner
          .withUserConfiguration(ConditionalTestConfiguration.class)
          .run(context ->
            Assertions.assertThrows(NoSuchBeanDefinitionException.class, () ->
              context.getBean(DevEnvLoggingConfiguration.class)
            )
          );
        contextRunner
          .withUserConfiguration(ConditionalTestConfiguration.class)
          .run(context ->
            Assertions.assertThrows(NoSuchBeanDefinitionException.class, () ->
              context.getBean(LoggingService.class)
            )
          );
    }

}
