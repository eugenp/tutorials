package com.baeldung.formatinstantobjectmapper;

import java.time.Instant;

import com.baeldung.formatinstantobjectmapper.utils.TimeStampTracker;

public class History implements TimeStampTracker {

    private String name;

    private Instant timeStamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
}
