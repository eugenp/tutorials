package com.baeldung.apache.avro;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializeEnumValueInAvroUnitTest {

    @TempDir
    Path tempDir;

    private Schema colorEnum;
    private Schema recordSchema;
    private Schema unionSchema;
    private Schema recordWithUnionSchema;

    @BeforeEach
    void setUp() {
        // Create enum schema
        colorEnum = SchemaBuilder.enumeration("Color")
                .namespace("com.baeldung.apache.avro")
                .symbols("UNKNOWN", "GREEN", "RED", "BLUE");

        // Create record schema with enum field
        recordSchema = SchemaBuilder.record("ColorRecord")
                .namespace("com.baeldung.apache.avro")
                .fields()
                .name("color")
                .type(colorEnum)
                .noDefault()
                .endRecord();

        // Create union schema with enum and null
        unionSchema = SchemaBuilder.unionOf()
                .type(colorEnum)
                .and()
                .nullType()
                .endUnion();

        // Create record schema with union field
        recordWithUnionSchema = SchemaBuilder.record("ColorRecordWithUnion")
                .namespace("com.baeldung.apache.avro")
                .fields()
                .name("color")
                .type(unionSchema)
                .noDefault()
                .endRecord();
    }

    @Test
    void whenSerializingEnum_thenSuccess() throws IOException {
        File file = tempDir.resolve("color.avro").toFile();

        // Create record with enum value
        GenericRecord record = new GenericData.Record(recordSchema);
        GenericData.EnumSymbol colorSymbol = new GenericData.EnumSymbol(colorEnum, "RED");
        record.put("color", colorSymbol);

        // Write to file
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(recordSchema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(recordSchema, file);
            dataFileWriter.append(record);
        }

        // Read from file
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(recordSchema);
        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            GenericRecord result = dataFileReader.next();
            assertEquals("RED", result.get("color").toString());
        }
    }

    @Test
    void whenSerializingEnumInUnion_thenSuccess() throws IOException {
        File file = tempDir.resolve("colorUnion.avro").toFile();

        // Create record with enum in union
        GenericRecord record = new GenericData.Record(recordWithUnionSchema);
        GenericData.EnumSymbol colorSymbol = new GenericData.EnumSymbol(colorEnum, "GREEN");
        record.put("color", colorSymbol);

        // Write to file
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(recordWithUnionSchema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(recordWithUnionSchema, file);
            dataFileWriter.append(record);
        }

        // Read from file
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(recordWithUnionSchema);
        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            GenericRecord result = dataFileReader.next();
            assertEquals("GREEN", result.get("color").toString());
        }
    }

    @Test
    void whenSerializingNullInUnion_thenSuccess() throws IOException {
        File file = tempDir.resolve("colorNull.avro").toFile();

        // Create record with null in union
        GenericRecord record = new GenericData.Record(recordWithUnionSchema);
        record.put("color", null);

        // Write to file
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(recordWithUnionSchema);
        assertDoesNotThrow(() -> {
            try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
                dataFileWriter.create(recordWithUnionSchema, file);
                dataFileWriter.append(record);
            }
        });
    }

    @Test
    void whenSchemaEvolution_thenDefaultValueUsed() throws IOException {
        // Create schema with new enum value and default at schema level
        String evolvedSchemaJson = "{\"type\":\"record\"," +
                "\"name\":\"ColorRecord\"," +
                "\"namespace\":\"com.baeldung.apache.avro\"," +
                "\"fields\":[{\"name\":\"color\"," +
                "\"type\":{\"type\":\"enum\"," +
                "\"name\":\"Color\"," +
                "\"symbols\":[\"UNKNOWN\",\"GREEN\",\"RED\",\"BLUE\",\"YELLOW\"]," +
                "\"default\":\"UNKNOWN\"}}]}";

        Schema evolvedRecordSchema = new Schema.Parser().parse(evolvedSchemaJson);
        Schema evolvedEnum = evolvedRecordSchema.getField("color").schema();

        File file = tempDir.resolve("colorEvolved.avro").toFile();

        // Create record with new enum value
        GenericRecord record = new GenericData.Record(evolvedRecordSchema);
        GenericData.EnumSymbol colorSymbol = new GenericData.EnumSymbol(evolvedEnum, "YELLOW");
        record.put("color", colorSymbol);

        // Write with evolved schema
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(evolvedRecordSchema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(evolvedRecordSchema, file);
            dataFileWriter.append(record);
        }

        // Create old schema without YELLOW but WITH default
        String originalSchemaJson = "{\"type\":\"record\"," +
                "\"name\":\"ColorRecord\"," +
                "\"namespace\":\"com.baeldung.apache.avro\"," +
                "\"fields\":[{\"name\":\"color\",\"type\":{\"type\":\"enum\",\"name\":\"Color\"," +
                "\"symbols\":[\"UNKNOWN\",\"GREEN\",\"RED\",\"BLUE\"]," +
                "\"default\":\"UNKNOWN\"}}]}";

        Schema originalRecordSchema = new Schema.Parser().parse(originalSchemaJson);

        // Read with original schema
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(evolvedRecordSchema, originalRecordSchema);
        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            GenericRecord result = dataFileReader.next();
            assertEquals("UNKNOWN", result.get("color").toString());
        }
    }
}
