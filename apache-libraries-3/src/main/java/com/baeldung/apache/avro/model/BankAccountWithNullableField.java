package com.baeldung.apache.avro.model;

import org.apache.avro.reflect.Nullable;

public record BankAccountWithNullableField(String bankAccountNumber, @Nullable String reference) {
}