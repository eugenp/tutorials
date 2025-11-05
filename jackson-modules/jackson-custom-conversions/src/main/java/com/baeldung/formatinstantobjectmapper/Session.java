package com.baeldung.formatinstantobjectmapper;

import java.time.Instant;

import com.baeldung.formatinstantobjectmapper.utils.Instants;
import com.baeldung.formatinstantobjectmapper.utils.TimeStampTracker;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Session implements TimeStampTracker {

    private String name;

    @JsonFormat(pattern = Instants.DATE_FORMAT, timezone = "UTC")
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
