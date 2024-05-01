package com.baeldung.avroschemasfromjavaclasses.avroreflectapi.customschemaforfield;

import java.util.List;

import org.apache.avro.reflect.AvroSchema;

public class BankAccount {
    private String number;

    @AvroSchema("{\"type\":\"array\",\"items\":[\"null\",\"int\"]}")
    private List<Integer> currentMonthOperationsDays;
}
