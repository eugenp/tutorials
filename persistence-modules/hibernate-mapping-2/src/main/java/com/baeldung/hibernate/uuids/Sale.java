package com.baeldung.hibernate.uuids;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class Sale {

    @Id
    @UuidGenerator
    private UUID id;

    private boolean completed;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}