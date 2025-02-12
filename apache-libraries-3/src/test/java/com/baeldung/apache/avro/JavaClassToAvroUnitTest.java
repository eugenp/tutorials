package com.baeldung.apache.avro;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.junit.jupiter.api.Test;

import com.baeldung.apache.avro.model.BankAccountWithIgnoredField;
import com.baeldung.apache.avro.model.BankAccountWithNullableField;
import com.baeldung.apache.avro.model.BankAccountWithOverriddenField;
import com.baeldung.apache.avro.model.SimpleBankAccount;

class JavaClassToAvroUnitTest {

    @Test
    void givenSimpleRecord_whenGeneratingSchema_thenAValidAvroSchemaIsReturned() {
        Schema schema = ReflectData.get()
            .getSchema(SimpleBankAccount.class);
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "BankAccount",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              } ]
            }
            """);
    }

    @Test
    void givenRecordWithNullableField_whenGeneratingSchema_thenAValidAvroSchemaIsReturned() {
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
    void givenRecordWithIgnoredField_whenGeneratingSchema_thenAValidAvroSchemaIsReturned() {
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
    void givenRecordWithOverriddenField_whenGeneratingSchema_thenAValidAvroSchemaIsReturned() {
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

}