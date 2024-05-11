package com.baeldung.avroschemasfromjavaclasses.jackson;

import org.apache.avro.Schema;

import com.baeldung.avroschemasfromjavaclasses.jackson.simplerecord.BankAccount;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;

public class ConverterApp {

    public static void main(String[] args) throws JsonMappingException {
        AvroMapper avroMapper = new AvroMapper();
        AvroSchemaGenerator avroSchemaGenerator = new AvroSchemaGenerator();

        avroMapper.acceptJsonFormatVisitor(BankAccount.class, avroSchemaGenerator);
        AvroSchema generatedSchema = avroSchemaGenerator.getGeneratedSchema();

        Schema bankAccountAvroSchema = generatedSchema.getAvroSchema();
        System.out.println(bankAccountAvroSchema.toString(true));
    }
}
