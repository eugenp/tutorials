package com.baeldung.cucumber.sharedatasteps;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EventSteps {
    public static final String UUID = "1ed80153-666c-4904-8e03-08c4a41e716a";
    public static final String CREATED_AT = "2024-12-03T09:00:00Z";
    public static final String PROCESSED_AT = "2024-12-03T10:00:00Z";

    @Autowired
    private SharedEvent sharedEvent;

    @When("new event enters the system")
    public void createNewEvent() {
        Event event = new Event();
        event.setStatus(EventStatus.PROCESSING);
        event.setUuid(UUID);
        sharedEvent.setEvent(event);
        sharedEvent.setCreatedAt(Instant.parse(CREATED_AT));
    }

    @Then("event is properly initialized")
    public void verifyEventIsInitialized() {
        Event event = sharedEvent.getEvent();
        assertThat(event.getStatus()).isEqualTo(EventStatus.PROCESSING);
        assertThat(event.getUuid()).isEqualTo(UUID);
        assertThat(sharedEvent.getCreatedAt().toString()).isEqualTo(CREATED_AT);
        assertThat(sharedEvent.getProcessedAt()).isNull();
    }

    @When("event processing (succeeds|fails)$")
    public void processEvent(String processingStatus) {
        // process event ...

        EventStatus eventStatus = "succeeds".equalsIgnoreCase(processingStatus) ?
          EventStatus.COMPLETE : EventStatus.ERROR;
        sharedEvent.getEvent().setStatus(eventStatus);
        sharedEvent.setProcessedAt(Instant.parse(PROCESSED_AT));
    }

    @Then("event has {status} status")
    public void verifyEventStatus(EventStatus status) {
        assertThat(sharedEvent.getEvent().getStatus()).isEqualTo(status);
    }

    @Then("event has processedAt")
    public void verifyProcessedAt() {
        assertThat(sharedEvent.getProcessedAt().toString()).isEqualTo(PROCESSED_AT);
    }

    @ParameterType("PROCESSING|ERROR|COMPLETE")
    public EventStatus status(String statusName) {
        return EventStatus.valueOf(statusName);
    }
}