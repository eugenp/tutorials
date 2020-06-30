package com.baeldung.hexagonalarchitecture.application.response;

import java.util.UUID;

public class AddTicketResponse {
    private final UUID id;

    public AddTicketResponse(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
