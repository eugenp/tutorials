package com.baeldung.mock.server.multiplerequests;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentGatewayResponse(UUID id, PaymentStatus status) {

    public enum PaymentStatus {
        PENDING,
        AUTHORIZED,
        DECLINED,
        REJECTED
    }
}
