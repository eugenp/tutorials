package com.baeldung.reactive.debugging.consumer.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class ListAppender extends AppenderBase<ILoggingEvent> {

    private static final List<ILoggingEvent> EVENTS = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent eventObject) {
        EVENTS.add(eventObject);
    }

    public static List<ILoggingEvent> getEvents() {
        return EVENTS;
    }

    public static void clearEventList() {
        EVENTS.clear();
    }
}
