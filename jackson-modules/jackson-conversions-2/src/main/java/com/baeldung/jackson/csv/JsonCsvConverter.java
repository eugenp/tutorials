package com.baeldung.jackson.csv;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

public class JsonCsvConverter {
    
    public static void JsonToCsv(File jsonFile, File csvFile) throws IOException {
        JsonNode jsonTree = new ObjectMapper().readTree(jsonFile);
        
        Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
        CsvSchema csvSchema = csvSchemaBuilder
            .build()
            .withHeader();
        
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
            .with(csvSchema)
            .writeValue(csvFile, jsonTree);
    }
    
    public static void csvToJson(File csvFile, File jsonFile) throws IOException {
        CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<OrderLine> orderLines = csvMapper.readerFor(OrderLine.class)
            .with(orderLineSchema)
            .readValues(csvFile);
        
        new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .writeValue(jsonFile, orderLines.readAll());
    }

    public static void JsonToFormattedCsv(File jsonFile, File csvFile) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
            .schemaFor(OrderLineForCsv.class)
            .withHeader();
        csvMapper.addMixIn(OrderLine.class, OrderLineForCsv.class);
        
        OrderLine[] orderLines = new ObjectMapper()
            .readValue(jsonFile, OrderLine[].class);
        csvMapper.writerFor(OrderLine[].class)
            .with(csvSchema)
            .writeValue(csvFile, orderLines);
    }
}
