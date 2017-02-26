package com.baeldung.beaninjection.domain;

public class AutomatedTellerMachine {

    private Transaction transaction;

    public AutomatedTellerMachine(Transaction transaction) {
        this.transaction = transaction;
    }

}
