package com.baeldung.avroschemasfromjavaclasses.jackson.simplerecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankAccount {
    @JsonProperty(required = true) private String number;
    private String accountHolderName;

    public String getNumber() {
        return number;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
}
