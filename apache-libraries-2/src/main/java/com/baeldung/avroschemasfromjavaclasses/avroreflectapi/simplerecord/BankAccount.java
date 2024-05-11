package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.simplerecord;

import org.apache.avro.reflect.Nullable;

public class BankAccount {
    private String number;

    @Nullable
    private BankAccountHolder holder;
}
