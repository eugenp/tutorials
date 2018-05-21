package com.baeldung.reactive.evaluation;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private Instant createdAt;

    Event() {
        this(-1, Instant.now());
    }

    Event(long id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}
