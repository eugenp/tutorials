package com.baeldung.scheduling;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledJobsWithConditional
{
    /**
     * This uses @ConditionalOnProperty to conditionally create a bean, which itself
     * is a scheduled job.
     * @return ScheduledJob
     */
    @Bean
    @ConditionalOnProperty(value = "jobs.enabled", matchIfMissing = true, havingValue = "true")
    public ScheduledJob runMyCronTask() {
        return new ScheduledJob("@ConditionalOnProperty");
    }
}
