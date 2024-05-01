package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.union;

import org.apache.avro.reflect.Union;

public class BankAccount {
    private String number;

    @Union(value = {
        ChildBankAccountHolder.class,
        AdultBankAccountHolder.class
    })
    private BankAccountHolder holder;
}
