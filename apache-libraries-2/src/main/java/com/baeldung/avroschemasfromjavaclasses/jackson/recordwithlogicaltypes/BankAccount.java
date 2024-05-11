package com.baeldung.avroschemasfromjavaclasses.jackson.recordwithlogicaltypes;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankAccount {
    @JsonProperty(required = true)
    private String number;

    @JsonProperty(required = true)
    private LocalDateTime createdAt;

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
