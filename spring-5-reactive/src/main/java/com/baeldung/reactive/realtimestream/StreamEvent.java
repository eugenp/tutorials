package com.baeldung.reactive.realtimestream;

import java.time.LocalDateTime;

public class StreamEvent {
    private String id;
    private LocalDateTime date;

    public StreamEvent(String id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

}
