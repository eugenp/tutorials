package com.baeldung.hexagonal.domain.core;

import lombok.Value;

@Value(staticConstructor = "of")
public class PaymentDetail {
    private final String paymentId;
    private final String debtor;
    private final String creditor;
    private final double amount;
    private final String currency;
    private final Status status;

    public enum Status {
        INITIALIZED, PROCESSED;
    }
}
