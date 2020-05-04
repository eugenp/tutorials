package com.baeldung.hexagonal.banking.domain;

import java.math.BigDecimal;

public class ConsumerAccount extends Account {
    public ConsumerAccount(Person person, Long accountNumber, int pin, BigDecimal currentBalance) {
        super(person, accountNumber, pin, currentBalance);

    }
}
