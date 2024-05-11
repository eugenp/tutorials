package com.baeldung.avroschemasfromjavaclasses.jackson.simplerecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankAccountHolder {
    @JsonProperty(required = true)
    private String firstName;

    @JsonProperty(required = true)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
