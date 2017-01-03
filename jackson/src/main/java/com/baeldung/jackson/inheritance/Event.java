package com.baeldung.jackson.inheritance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "eventType")
public class Event {
    private final Metadata metadata;

    @JsonCreator
    public Event(Metadata metadata) {
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public static class Metadata {
        private final String id;
        private final Long timestamp;

        public Metadata(@JsonProperty("id") String id, @JsonProperty("timestamp") Long timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }

        public String getId() {
            return id;
        }

        public Long getTimestamp() {
            return timestamp;
        }
    }
}