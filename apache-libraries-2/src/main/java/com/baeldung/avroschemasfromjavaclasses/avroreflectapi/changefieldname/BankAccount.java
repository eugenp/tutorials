package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.changefieldname;

import org.apache.avro.reflect.AvroName;

public class BankAccount {
    @AvroName("bankAccountNumber")
    private String number;
}
