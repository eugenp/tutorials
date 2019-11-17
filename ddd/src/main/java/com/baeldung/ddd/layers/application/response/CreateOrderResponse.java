package com.baeldung.ddd.layers.application.response;

import org.bson.types.ObjectId;

public class CreateOrderResponse {
    private final String id;

    public CreateOrderResponse(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
