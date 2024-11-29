package com.baeldung.hibernate.timezonestorage;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.TimeZoneColumn;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "astronomical_observations")
public class AstronomicalObservation {

    @Id
    private UUID id;

    private String celestialObjectName;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "observation_start_time", columnDefinition = "TIMESTAMP(9) WITH TIME ZONE")
    private ZonedDateTime observationStartTime;

    @TimeZoneStorage(TimeZoneStorageType.COLUMN)
    @TimeZoneColumn(name = "peak_visibility_time_offset")
    @Column(name = "peak_visibility_time", columnDefinition = "TIMESTAMP(9)")
    private OffsetDateTime peakVisibilityTime;

    @Column(name = "peak_visibility_time_offset", insertable = false, updatable = false)
    private Integer peakVisibilityTimeOffset;

    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    @Column(name = "next_expected_appearance", columnDefinition = "TIMESTAMP(9)")
    private ZonedDateTime nextExpectedAppearance;

    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
    @Column(name = "last_recorded_sighting", columnDefinition = "TIMESTAMP(9)")
    private OffsetDateTime lastRecordedSighting;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCelestialObjectName() {
        return celestialObjectName;
    }

    public void setCelestialObjectName(String celestialObjectName) {
        this.celestialObjectName = celestialObjectName;
    }

    public ZonedDateTime getObservationStartTime() {
        return observationStartTime;
    }

    public void setObservationStartTime(ZonedDateTime observationStartTime) {
        this.observationStartTime = observationStartTime;
    }

    public OffsetDateTime getPeakVisibilityTime() {
        return peakVisibilityTime;
    }

    public void setPeakVisibilityTime(OffsetDateTime peakVisibilityTime) {
        this.peakVisibilityTime = peakVisibilityTime;
    }

    public Integer getPeakVisibilityTimeOffset() {
        return peakVisibilityTimeOffset;
    }

    public void setPeakVisibilityTimeOffset(Integer peakVisibilityTimeOffset) {
        this.peakVisibilityTimeOffset = peakVisibilityTimeOffset;
    }

    public ZonedDateTime getNextExpectedAppearance() {
        return nextExpectedAppearance;
    }

    public void setNextExpectedAppearance(ZonedDateTime nextExpectedAppearance) {
        this.nextExpectedAppearance = nextExpectedAppearance;
    }

    public OffsetDateTime getLastRecordedSighting() {
        return lastRecordedSighting;
    }

    public void setLastRecordedSighting(OffsetDateTime lastRecordedSighting) {
        this.lastRecordedSighting = lastRecordedSighting;
    }

}