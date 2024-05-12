package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.ignorefield;

import org.apache.avro.reflect.AvroIgnore;

public class BankAccount {
    private String number;
    @AvroIgnore private String password;
}
