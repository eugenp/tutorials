package com.baeldung.tutorials.openapi.quotes.config;

import java.time.Clock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfiguration {

    @Bean
    @ConditionalOnMissingBean
    Clock defaultClock() {
        return Clock.systemDefaultZone();
    }
}
