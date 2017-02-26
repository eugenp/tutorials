package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutomatedTellerMachine {

    private Transaction transaction;

    @Autowired
    public AutomatedTellerMachine(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return String.format("Transaction: %s", transaction);
    }
}
