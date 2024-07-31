package com.baeldung.datastax.cassandra.domain;

import java.time.Instant;
import java.util.UUID;

public class Video {

    private UUID id;
    private String title;
    private Instant creationDate;

    public Video(UUID id, String title, Instant creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
    }

    public Video(String title, Instant creationDate) {
        this.title = title;
        this.creationDate = creationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "[id:" + id.toString() + ", title:" + title + ", creationDate: " + creationDate.toString() + "]";
    }
}
