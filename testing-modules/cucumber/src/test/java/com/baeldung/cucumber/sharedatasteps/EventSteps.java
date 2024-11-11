package com.baeldung.sharedatasteps;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EventSteps {

    @Autowired
    private SharedEvent sharedEvent;

    @When("new event enters the system")
    public void createNewEvent() {
        Event event = new Event();
        event.setStatus(EventStatus.PROCESSING);
        event.setUuid(UUID.randomUUID().toString());
        sharedEvent.setEvent(event);
        sharedEvent.setCreatedAt(Instant.now());
    }

    @Then("event is properly initialized")
    public void verifyEventIsInitialized() {
        Event event = sharedEvent.getEvent();
        assertThat(event.getStatus()).isEqualTo(EventStatus.PROCESSING);
        assertThat(event.getUuid()).isNotNull();
        assertThat(sharedEvent.getCreatedAt()).isNotNull();
        assertThat(sharedEvent.getProcessedAt()).isNull();
    }

    @When("event processing (succeeds|fails)$")
    public void processEvent(String processingStatus) {
        // process event ...

        EventStatus eventStatus = "succeeds".equalsIgnoreCase(processingStatus) ?
            EventStatus.COMPLETE : EventStatus.ERROR;
        sharedEvent.getEvent().setStatus(eventStatus);
        sharedEvent.setProcessedAt(Instant.now());
    }

    @Then("event has {status} status")
    public void verifyEventStatus(EventStatus status) {
        assertThat(sharedEvent.getEvent().getStatus()).isEqualTo(status);
    }

    @Then("event has processedAt")
    public void verifyProcessedAt() {
        assertThat(sharedEvent.getProcessedAt()).isNotNull();
    }

    @ParameterType("PROCESSING|ERROR|COMPLETE")
    public EventStatus status(String statusName) {
        return EventStatus.valueOf(statusName);
    }
}