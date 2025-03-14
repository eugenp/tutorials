package com.baeldung.apache.avro;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import org.apache.avro.Conversions;
import org.apache.avro.Schema;
import org.apache.avro.data.TimeConversions;
import org.apache.avro.reflect.ReflectData;
import org.junit.jupiter.api.Test;

import com.baeldung.apache.avro.model.BankAccountWithAbstractField;
import com.baeldung.apache.avro.model.BankAccountWithIgnoredField;
import com.baeldung.apache.avro.model.BankAccountWithLogicalTypes;
import com.baeldung.apache.avro.model.BankAccountWithNullableField;
import com.baeldung.apache.avro.model.BankAccountWithOverriddenField;
import com.baeldung.apache.avro.model.SimpleBankAccount;

class ReflectDataUnitTest {

    @Test
    void whenConvertingSimpleRecord_thenAvroSchemaIsCorrect() {
        Schema schema = ReflectData.get()
            .getSchema(SimpleBankAccount.class);
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "SimpleBankAccount",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              } ]
            }
            """);
    }

    @Test
    void whenConvertingRecordWithNullableField_thenAvroSchemaIsCorrect() {
        Schema schema = ReflectData.get()
            .getSchema(BankAccountWithNullableField.class);
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "BankAccountWithNullableField",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              }, {
                "name" : "reference",
                "type" : [ "null", "string" ],
                "default" : null
              } ]
            }
            """);
    }

    @Test
    void whenConvertingRecordWithIgnoredField_thenAvroSchemaIsCorrect() {
        Schema schema = ReflectData.get()
            .getSchema(BankAccountWithIgnoredField.class);
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "BankAccountWithIgnoredField",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              } ]
            }
            """);
    }

    @Test
    void whenConvertingRecordWithOverriddenField_thenAvroSchemaIsCorrect() {
        Schema schema = ReflectData.get()
            .getSchema(BankAccountWithOverriddenField.class);
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "BankAccountWithOverriddenField",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              }, {
                "name" : "bankAccountReference",
                "type" : "string"
              } ]
            }
            """);
    }

    @Test
    void whenConvertingRecordWithAbstractField_thenAvroSchemaIsCorrect() {
        Schema schema = ReflectData.get()
            .getSchema(BankAccountWithAbstractField.class);
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "BankAccountWithAbstractField",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              }, {
                "name" : "reference",
                "type" : [ {
                  "type" : "record",
                  "name" : "PersonalBankAccountReference",
                  "namespace" : "com.baeldung.apache.avro.model.BankAccountWithAbstractField",
                  "fields" : [ {
                    "name" : "holderName",
                    "type" : "string"
                  }, {
                    "name" : "reference",
                    "type" : "string"
                  } ]
                }, {
                  "type" : "record",
                  "name" : "BusinessBankAccountReference",
                  "namespace" : "com.baeldung.apache.avro.model.BankAccountWithAbstractField",
                  "fields" : [ {
                    "name" : "businessEntityId",
                    "type" : "string"
                  }, {
                    "name" : "reference",
                    "type" : "string"
                  } ]
                } ]
              } ]
            }
            """);
    }

    @Test
    void whenConvertingRecordWithLogicalTypes_thenAvroSchemaIsCorrect() {
        ReflectData reflectData = ReflectData.get();
        reflectData.addLogicalTypeConversion(new Conversions.UUIDConversion());
        reflectData.addLogicalTypeConversion(new TimeConversions.LocalTimestampMillisConversion());

        String jsonSchema = reflectData.getSchema(BankAccountWithLogicalTypes.class)
            .toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "BankAccountWithLogicalTypes",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              }, {
                "name" : "expiryDate",
                "type" : {
                  "type" : "long",
                  "logicalType" : "local-timestamp-millis"
                }
              }, {
                "name" : "reference",
                "type" : {
                  "type" : "string",
                  "logicalType" : "uuid"
                }
              } ]
            }
            """);
    }

}