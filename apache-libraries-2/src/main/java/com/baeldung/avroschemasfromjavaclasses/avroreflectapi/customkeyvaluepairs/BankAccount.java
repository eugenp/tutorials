package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.customkeyvaluepairs;

import org.apache.avro.reflect.AvroMeta;

public class BankAccount {
    @AvroMeta(key = "format", value = "IBAN")
    private String number;
}
