package com.baeldung.constructordetector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StringWrapper {
    private String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public StringWrapper(@JsonProperty("value") String value) {
        this.value = value;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }
}

