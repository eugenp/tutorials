package com.baeldung.offsetdatetime;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class User implements Serializable {
    private OffsetDateTime createdAt;

    // constructors, getters and setters

    public User() {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}