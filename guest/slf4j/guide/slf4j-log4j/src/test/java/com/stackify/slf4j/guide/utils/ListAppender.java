package com.stackify.slf4j.guide.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class ListAppender extends AppenderSkeleton {
    public static List<LoggingEvent> events = new ArrayList<LoggingEvent>();

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    protected void append(LoggingEvent event) {
        events.add(event);
    }

    public static List<LoggingEvent> getEvents() {
        return events;
    }

    public static void clearEventList() {
        events.clear();
    }
}
