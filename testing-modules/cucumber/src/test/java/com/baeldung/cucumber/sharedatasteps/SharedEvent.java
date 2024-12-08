package com.baeldung.sharedatasteps;

import java.time.Instant;

import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;

@ScenarioScope
@Component
public class SharedEvent {
    private Event event;
    private Instant createdAt;
    private Instant processedAt;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }
}