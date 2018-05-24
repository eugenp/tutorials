package com.baeldung.functional.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class City {

    private final String city;

    @JsonCreator
    public City(@JsonProperty("city") final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
