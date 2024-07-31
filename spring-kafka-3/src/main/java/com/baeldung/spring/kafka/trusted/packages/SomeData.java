package com.baeldung.spring.kafka.trusted.packages;

import java.time.Instant;

public class SomeData {

    private String id;
    private String type;
    private String status;
    private Instant timestamp;

    public SomeData() {
    }

    public SomeData(String id, String type, String status, Instant timestamp) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
