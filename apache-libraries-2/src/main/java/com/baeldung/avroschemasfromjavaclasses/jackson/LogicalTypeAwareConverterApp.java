package com.baeldung.avroschemasfromjavaclasses.jackson;

import org.apache.avro.Schema;

import com.baeldung.avroschemasfromjavaclasses.jackson.recordwithlogicaltypes.BankAccount;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.jsr310.AvroJavaTimeModule;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;

public class LogicalTypeAwareConverterApp {

    public static void main(String[] args) throws JsonMappingException {
        AvroMapper avroMapper = AvroMapper.builder()
            .addModule(new AvroJavaTimeModule())
            .build();
        AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator();
        avroSchemaGenerator.enableLogicalTypes();

        avroMapper.acceptJsonFormatVisitor(BankAccount.class, avroSchemaGenerator);
        AvroSchema generatedSchema = avroSchemaGenerator.getGeneratedSchema();

        Schema bankAccountAvroSchema = generatedSchema.getAvroSchema();
        System.out.println(bankAccountAvroSchema.toString(true));
    }
}
