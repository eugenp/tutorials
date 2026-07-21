package com.baeldung.applicationstartuptracking;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> recorded() {

        List<String> recordedEvents = new ArrayList<>();

        for(StartupTimeline.TimelineEvent event : startup.getBufferedTimeline().getEvents()) {
            for(StartupStep.Tag tag : event.getStartupStep().getTags()) {
                recordedEvents.add(
                    event.getStartupStep().getName() + "  " +
                        tag.getKey() + " " +
                        tag.getValue()
                );
            }
        }

        return recordedEvents;
    }
}
