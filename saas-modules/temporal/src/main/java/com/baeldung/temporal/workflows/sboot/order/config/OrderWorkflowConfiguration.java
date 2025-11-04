package com.baeldung.temporal.workflows.sboot.order.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class OrderWorkflowConfiguration {

    @Bean
    @ConditionalOnMissingBean(Clock.class)
    Clock standardClock() {
        return Clock.systemDefaultZone();
    }
}
