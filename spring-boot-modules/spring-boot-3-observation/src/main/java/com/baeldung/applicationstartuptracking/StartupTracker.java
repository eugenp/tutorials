package com.baeldung.applicationstartuptracking;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.metrics.buffering.StartupTimeline;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Component;

@Component
public class StartupTracker {

    private BufferingApplicationStartup startup;

    public StartupTracker(@Autowired ApplicationContext context) {
        startup = (BufferingApplicationStartup) ((ConfigurableApplicationContext) context).getApplicationStartup();
    }

    public void record() {

        Instant startTime = startup.getBufferedTimeline().getStartTime();
        System.out.println(startTime);

        for(StartupTimeline.TimelineEvent event : startup.getBufferedTimeline().getEvents()) {
            System.out.println(event.getStartTime() + ": " + event.getStartupStep().getName());

            for(StartupStep.Tag tag : event.getStartupStep().getTags()) {
                System.out.println("  " + tag.getKey() + " " + tag.getValue());
            }
        }
    }
}
