package com.baeldung.apache.avro.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JacksonBankAccountWithRequiredField(@JsonProperty(required = true) String bankAccountNumber) {

}