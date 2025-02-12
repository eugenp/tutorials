package com.baeldung.apache.avro.model;

import org.apache.avro.reflect.AvroName;

public record BankAccountWithOverriddenField(String bankAccountNumber, @AvroName("bankAccountReference") String reference) {
}