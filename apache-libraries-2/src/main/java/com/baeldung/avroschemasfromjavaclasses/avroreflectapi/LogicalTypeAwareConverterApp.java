package com.baeldung.avroschemasfromjavaclasses.avroreflectapi;

import org.apache.avro.Conversions;
import org.apache.avro.Schema;
import org.apache.avro.data.TimeConversions;
import org.apache.avro.reflect.ReflectData;

import com.baeldung.avroschemasfromjavaclasses.avroreflectapi.simplerecord.BankAccount;

public class LogicalTypeAwareConverterApp {

    public static void main(String[] args) {
        ReflectData reflectData = ReflectData.get();
        reflectData.addLogicalTypeConversion(new Conversions.UUIDConversion());
        reflectData.addLogicalTypeConversion(new TimeConversions.LocalTimestampMillisConversion());

        Schema bankAccountSchema = reflectData.getSchema(BankAccount.class);

        String bankAccountSchemaJson = bankAccountSchema.toString(true);
        System.out.println(bankAccountSchemaJson);
    }
}
