package com.baeldung.concurrent.skiplist;

import java.time.ZonedDateTime;

public class Event {
    private final ZonedDateTime eventTime;
    private final String content;

    public Event(ZonedDateTime eventTime, String content) {
        this.eventTime = eventTime;
        this.content = content;
    }

    public ZonedDateTime getEventTime() {
        return eventTime;
    }

    public String getContent() {
        return content;
    }
}
