package com.baeldung.annotations.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(IsDevEnvCondition.class)
public class DevEnvLoggingConfiguration {

    @Bean
    @Conditional(IsDevEnvCondition.class)
    LoggingService loggingService() {
        return new LoggingService();
    }
}
