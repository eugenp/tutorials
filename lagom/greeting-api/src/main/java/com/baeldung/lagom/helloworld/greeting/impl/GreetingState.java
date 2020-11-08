package com.baeldung.lagom.helloworld.greeting.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class GreetingState {

    private final String message;

    @JsonCreator
    public GreetingState(String message) {
        this.message = message;
    }

    GreetingState withMessage(String message) {
        return new GreetingState(message);
    }

    public String getMessage() {
        return message;
    }

}
