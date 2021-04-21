package com.baeldung.model;

import java.time.Instant;
import java.util.UUID;

public class Response {
    private Instant timestamp;
    private UUID uuid;
    private String message;

    public Response(String message) {
        this.timestamp = Instant.now();
        this.uuid = UUID.randomUUID();
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
