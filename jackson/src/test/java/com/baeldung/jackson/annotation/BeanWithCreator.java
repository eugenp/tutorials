package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeanWithCreator {
    private int id;
    public String name;

    @JsonCreator
    public BeanWithCreator(@JsonProperty("id") final int id, @JsonProperty("theName") final String name) {
        this.id = id;
        this.name = name;
    }
}
