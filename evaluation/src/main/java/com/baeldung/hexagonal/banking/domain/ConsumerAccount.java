package com.baeldung.hexagonal.banking.domain;

import java.math.BigDecimal;

public class ConsumerAccount extends Account {

    public ConsumerAccount(Person person, int pin, BigDecimal currentBalance) {
        super(person, pin, currentBalance);

    }

    public ConsumerAccount(Long accountNumber, Person person, int pin, BigDecimal currentBalance) {
        super(accountNumber, person, pin, currentBalance);

    }
}
