package com.baeldung.temporal.workflows.sboot.order.config;

import com.baeldung.temporal.workflows.sboot.order.OrderWorkflow;
import com.baeldung.temporal.workflows.sboot.order.activities.OrderActivities;
import com.baeldung.temporal.workflows.sboot.order.activities.OrderActivitiesImpl;
import com.baeldung.temporal.workflows.sboot.order.activities.OrderActivitiesStub;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.autoconfigure.ServiceStubsAutoConfiguration;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Duration;
import java.util.function.Supplier;

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
