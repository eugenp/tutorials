package com.baeldung.apache.avro.schemafromavrofile;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AvroSchemaExtractorUnitTest {

    @TempDir
    Path tempDir;

    private File avroFile;
    private Schema schema;

    @BeforeEach
    void setUp() throws IOException {
        schema = new Schema.Parser().parse("""
                                        {
                                            "type": "record",
                                            "name": "User",
                                            "fields": [
                                                {"name": "name", "type": "string"},
                                                {"name": "age", "type": "int"}
                                            ]
                                        }
                                        """);
        avroFile = tempDir.resolve("test.avro").toFile();

        GenericRecord user1 = new GenericData.Record(schema);
        user1.put("name", "John Doe");
        user1.put("age", 30);

        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(new GenericDatumWriter<>(schema))) {
            dataFileWriter.create(schema, avroFile);
            dataFileWriter.append(user1);
        }
    }

    @Test
    void whenSchemaIsExistent_thenItIsExtractedCorrectly() throws IOException {
        Schema extractedSchema = AvroSchemaExtractor.extractSchema(avroFile.getPath());

        assertEquals(schema, extractedSchema);
    }

    @Test
    void whenAvroFileHasContent_thenItIsReadCorrectly() throws IOException {
        List<GenericRecord> records = AvroSchemaExtractor.readAvroData(avroFile.getPath());

        assertEquals("John Doe", records.get(0).get(0).toString());
    }
}