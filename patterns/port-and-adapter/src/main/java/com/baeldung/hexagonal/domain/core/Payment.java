package com.baeldung.hexagonal.domain.core;

import lombok.Value;

@Value
public class Payment {
    private final String debtor;
    private final String creditor;
    private final double amount;
    private final String currency;
}
