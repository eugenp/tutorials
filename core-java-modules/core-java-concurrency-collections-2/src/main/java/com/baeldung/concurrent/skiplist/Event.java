package com.baeldung.concurrent.skiplist;

import java.time.ZonedDateTime;

class Event {
    private final ZonedDateTime eventTime;
    private final String content;

    Event(ZonedDateTime eventTime, String content) {
        this.eventTime = eventTime;
        this.content = content;
    }

    ZonedDateTime getEventTime() {
        return eventTime;
    }

    String getContent() {
        return content;
    }
}
