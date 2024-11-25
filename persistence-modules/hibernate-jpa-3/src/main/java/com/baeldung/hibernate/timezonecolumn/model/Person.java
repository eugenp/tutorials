package com.baeldung.hibernate.timezonecolumn.model;

import jakarta.persistence.*;
import org.hibernate.annotations.TimeZoneColumn;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import java.time.OffsetDateTime;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "inserted_at_timestamp")
    private OffsetDateTime insertedAt;

    @Column(name = "updated_at_timestamp")
    @TimeZoneStorage(TimeZoneStorageType.COLUMN)
    @TimeZoneColumn(name="last_updated_offset")
    private OffsetDateTime lastUpdatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(OffsetDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }

    public OffsetDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(OffsetDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
