package com.baeldung.offsetdatetime;

import java.time.OffsetDateTime;

public class User {
    private OffsetDateTime createdAt;

    public User() {
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}