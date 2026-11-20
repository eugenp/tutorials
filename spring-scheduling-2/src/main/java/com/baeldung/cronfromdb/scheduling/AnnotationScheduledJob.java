package com.baeldung.cronfromdb.scheduling;

import static java.time.LocalTime.now;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnnotationScheduledJob {

    private static final Logger log = LoggerFactory.getLogger(AnnotationScheduledJob.class);
private String a = "foo";
    @Scheduled(cron = "#{@cronLoader}")
    public void run() {
        log.info("✅ [{}] Job executed - cron loaded from DB via @Scheduled", now());
    }
}