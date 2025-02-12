package com.baeldung.apache.avro.model;

import org.apache.avro.reflect.AvroIgnore;

public record BankAccountWithIgnoredField(String bankAccountNumber, @AvroIgnore String reference) {
}