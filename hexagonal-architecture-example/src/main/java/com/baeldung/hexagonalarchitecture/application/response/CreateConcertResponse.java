package com.baeldung.hexagonalarchitecture.application.response;

import java.util.UUID;

public class CreateConcertResponse {
    private final UUID id;

    public CreateConcertResponse(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
