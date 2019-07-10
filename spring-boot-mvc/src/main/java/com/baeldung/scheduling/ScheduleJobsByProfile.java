package com.baeldung.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ScheduleJobsByProfile {

    private final static Logger LOG = LoggerFactory.getLogger(ScheduleJobsByProfile.class);

    @Profile("prod")
    @Bean
    public ScheduledJob scheduledJob()
    {
        return new ScheduledJob("@Profile");
    }
}
