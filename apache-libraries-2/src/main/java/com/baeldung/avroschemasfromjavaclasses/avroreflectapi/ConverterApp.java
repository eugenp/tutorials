package com.baeldung.avroschemasfromjavaclasses.avroreflectapi;

import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

import com.baeldung.avroschemasfromjavaclasses.avroreflectapi.simplerecord.BankAccount;

public class ConverterApp {

    public static void main(String[] args) {
        ReflectData reflectData = ReflectData.get();
        reflectData.addLogicalTypeConversion(new Conversions.UUIDConversion());
        reflectData.addLogicalTypeConversion(new FixedScaleAndPrecisionDecimalConversion());

        Schema bankAccountSchema = reflectData.getSchema(BankAccount.class);

        String bankAccountSchemaJson = bankAccountSchema.toString(true);
        System.out.println(bankAccountSchemaJson);
    }
}

class FixedScaleAndPrecisionDecimalConversion extends Conversions.DecimalConversion {

    @Override
    public Schema getRecommendedSchema() {
        return LogicalTypes.decimal(14, 2).addToSchema(Schema.create(Schema.Type.BYTES));
    }
}
