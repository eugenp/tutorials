package com.baeldung.reactive.realtimestream;

import java.time.LocalDateTime;

public class StreamEvent {
    private String id;
    private LocalDateTime date;

    public StreamEvent() {
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StreamEvent{" +
                "id='" + id + '\'' +
                ", date=" + date +
                '}';
    }
}
