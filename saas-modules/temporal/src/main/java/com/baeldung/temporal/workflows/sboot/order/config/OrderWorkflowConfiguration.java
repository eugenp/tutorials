package com.baeldung.temporal.workflows.sboot.order.config;

import io.temporal.spring.boot.autoconfigure.ServiceStubsAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@AutoConfigureBefore(ServiceStubsAutoConfiguration.class)
public class OrderWorkflowConfiguration {

    private static Logger log = LoggerFactory.getLogger(OrderWorkflowConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(Clock.class)
    Clock standardClock() {
        return Clock.systemDefaultZone();
    }


}
