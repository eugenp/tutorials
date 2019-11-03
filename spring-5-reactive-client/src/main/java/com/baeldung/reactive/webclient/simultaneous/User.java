package com.baeldung.reactive.webclient.simultaneous;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private int id;

    @JsonCreator
    public User(@JsonProperty("id") int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}
