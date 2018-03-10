package org.baeldung.spring.webflux.securityincidents.domain;

import java.util.*;

public class SecurityEvent {
    private UUID id;
    private String name;
    private Date timestamp;

    public SecurityEvent(String name) {
        id = UUID.randomUUID();
        timestamp = new Date();
        this.name = name;
    }

    public SecurityEvent() {
        id = UUID.randomUUID();
        timestamp = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SecurityEvent [id=" + id + ", name=" 
          + name + ", timestamp=" + timestamp + "]";
    }
}
