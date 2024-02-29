package com.baeldung.micronaut.httpfilters;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class MemoryAppender extends AppenderBase<ILoggingEvent> {

    private final List<ILoggingEvent> events = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent e) {
        events.add(e);
    }

    public List<ILoggingEvent> getEvents() {
        return events;
    }
}