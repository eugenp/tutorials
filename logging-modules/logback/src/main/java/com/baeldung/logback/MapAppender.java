package com.baeldung.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.HashMap;
import java.util.Map;

public class MapAppender extends AppenderBase<ILoggingEvent> {

    private final Map<String, ILoggingEvent> eventMap = new HashMap<>();

    private String prefix;

    @Override
    protected void append(final ILoggingEvent event) {
        if (prefix == null || "".equals(prefix)) {
            addError("Prefix is not set for MapAppender.");
            return;
        }

        eventMap.put(prefix + System.currentTimeMillis(), event);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public Map<String, ILoggingEvent> getEventMap() {
        return eventMap;
    }

}
