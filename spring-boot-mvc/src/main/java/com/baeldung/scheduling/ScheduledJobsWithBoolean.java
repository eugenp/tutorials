package com.baeldung.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduledJobsWithBoolean {

    private final static Logger LOG = LoggerFactory.getLogger(ScheduledJobsWithBoolean.class);

    @Value("${jobs.enabled:true}")
    private boolean isEnabled;

    /**
     * A scheduled job controlled via application property. The job always
     * executes, but the logic inside is protected by a configurable boolean
     * flag.
     */
    @Scheduled(fixedDelay = 60000)
    public void cleanTempDirectory() {
        if(isEnabled) {
            LOG.info("Cleaning temp directory via boolean flag");
        }
    }
}
