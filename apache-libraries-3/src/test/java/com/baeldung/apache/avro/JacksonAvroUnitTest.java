package com.baeldung.apache.avro;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import org.apache.avro.Schema;
import org.junit.jupiter.api.Test;

import com.baeldung.apache.avro.model.BankAccountWithLogicalTypes;
import com.baeldung.apache.avro.model.JacksonBankAccountWithRequiredField;
import com.baeldung.apache.avro.model.SimpleBankAccount;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;

class JacksonAvroUnitTest {

    @Test
    void whenConvertingRecord_thenAvroSchemaIsCorrect() throws JsonMappingException {
        AvroMapper avroMapper = new AvroMapper();
        AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator();

        avroMapper.acceptJsonFormatVisitor(SimpleBankAccount.class, avroSchemaGenerator);
        Schema schema = avroSchemaGenerator.getGeneratedSchema()
            .getAvroSchema();
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "SimpleBankAccount",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : [ "null", "string" ]
              } ]
            }
            """);
    }

    @Test
    void whenConvertingRecordWithRequiredField_thenAvroSchemaIsCorrect() throws JsonMappingException {
        AvroMapper avroMapper = new AvroMapper();
        AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator();

        avroMapper.acceptJsonFormatVisitor(JacksonBankAccountWithRequiredField.class, avroSchemaGenerator);
        Schema schema = avroSchemaGenerator.getGeneratedSchema()
            .getAvroSchema();
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "JacksonBankAccountWithRequiredField",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : "string"
              } ]
            }
            """);
    }

    @Test
    void whenConvertingRecordWithLogicalField_whenGeneratingSchemaWithJackson_thenAvroSchemaIsCorrect() throws JsonMappingException {
        AvroMapper avroMapper = AvroMapper.builder()
            .addModule(new AvroJavaTimeModule())
            .build();

        AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator().enableLogicalTypes();

        avroMapper.acceptJsonFormatVisitor(BankAccountWithLogicalTypes.class, avroSchemaGenerator);
        Schema schema = avroSchemaGenerator.getGeneratedSchema()
            .getAvroSchema();
        String jsonSchema = schema.toString();

        assertThatJson(jsonSchema).isEqualTo("""
            {
              "type" : "record",
              "name" : "BankAccountWithLogicalTypes",
              "namespace" : "com.baeldung.apache.avro.model",
              "fields" : [ {
                "name" : "bankAccountNumber",
                "type" : [ "null", "string" ]
              }, {
                "name" : "expiryDate",
                "type" : [ "null", {
                  "type" : "long",
                  "logicalType" : "local-timestamp-millis"
                } ]
              }, {
                "name" : "reference",
                "type" : [ "null", {
                  "type" : "fixed",
                  "name" : "UUID",
                  "namespace" : "java.util",
                  "doc" : "",
                  "size" : 16
                } ]
              } ]
            }
            """);
    }

}
