package com.baeldung.sorting;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolarEventContainer {

    @JsonProperty("solar_events")
    private List<SolarEvent> solarEvents;

    // Getters and setters
    public List<SolarEvent> getSolarEvents() {
        return solarEvents;
    }

    public void setSolarEvents(List<SolarEvent> solarEvents) {
        this.solarEvents = solarEvents;
    }
}
