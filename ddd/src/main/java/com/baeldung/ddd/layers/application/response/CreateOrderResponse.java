package com.baeldung.ddd.layers.application.response;

import java.util.UUID;

public class CreateOrderResponse {
    private final UUID id;

    public CreateOrderResponse(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
