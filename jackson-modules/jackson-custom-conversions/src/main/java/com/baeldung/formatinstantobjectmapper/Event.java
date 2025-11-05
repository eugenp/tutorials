package com.baeldung.formatinstantobjectmapper;

import java.time.Instant;

import com.baeldung.formatinstantobjectmapper.serializer.CustomInstantDeserializer;
import com.baeldung.formatinstantobjectmapper.serializer.CustomInstantSerializer;
import com.baeldung.formatinstantobjectmapper.utils.TimeStampTracker;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Event implements TimeStampTracker {

    private String name;

    @JsonSerialize(using = CustomInstantSerializer.class)
    @JsonDeserialize(using = CustomInstantDeserializer.class)
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
