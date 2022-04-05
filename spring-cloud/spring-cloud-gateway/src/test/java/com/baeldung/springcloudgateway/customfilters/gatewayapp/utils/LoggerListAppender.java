package com.baeldung.springcloudgateway.customfilters.gatewayapp.utils;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class LoggerListAppender extends AppenderBase<ILoggingEvent> {

    static private List<ILoggingEvent> events = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent eventObject) {
        events.add(eventObject);
    }

    public static List<ILoggingEvent> getEvents() {
        return events;
    }

    public static void clearEventList() {
        events.clear();
    }
}