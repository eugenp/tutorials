package com.baeldung.avroschemasfromjavaclasses.jackson.simplerecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankAccount {
    @JsonProperty(required = true)
    private String number;

    private BankAccountHolder holder;

    public String getNumber() {
        return number;
    }

    public BankAccountHolder getHolder() {
        return holder;
    }
}
