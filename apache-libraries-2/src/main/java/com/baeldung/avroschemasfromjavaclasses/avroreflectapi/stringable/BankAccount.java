package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.stringable;

import org.apache.avro.reflect.Stringable;

public class BankAccount {
    private String number;

    @Stringable
    private Integer holderAge;
}
