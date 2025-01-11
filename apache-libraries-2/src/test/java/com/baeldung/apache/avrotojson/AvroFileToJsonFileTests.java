package com.baeldung.apache.avrotojson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.avro.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AvroFileToJsonFileTests {

    private AvroFileToJsonFile avroFileToJsonFile;
    private File dataLocation;
    private File jsonDataLocation;
    private Point p;
    private String expectedOutput;

    @BeforeEach
    public void setup() {
        avroFileToJsonFile = new AvroFileToJsonFile();
        // Load files from the resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        dataLocation = new File(classLoader.getResource("").getFile(), "data.avro");
        jsonDataLocation = new File(classLoader.getResource("").getFile(), "data.json");
        p = new Point(2, 4);
        expectedOutput = "{\"x\":2,\"y\":4}";
    }

    @Test
    public void whenConvertedToJson_ThenEquals() {
        String response = avroFileToJsonFile.convertObjectToJson(p, avroFileToJsonFile.inferSchema(p));
        assertEquals(expectedOutput, response);
    }

    @Test
    public void whenAvroContentWrittenToFile_ThenExist() {
        Schema schema = avroFileToJsonFile.inferSchema(p);
        avroFileToJsonFile.writeAvroToFile(schema, List.of(p), dataLocation);
        assertTrue(dataLocation.exists());
    }

    @Test
    public void whenAvroFileWrittenToJsonFile_ThenJsonContentEquals() throws IOException {
        // read avro to json
        avroFileToJsonFile.readAvroFromFileToJsonFile(dataLocation, jsonDataLocation);
        // read json file content
        String text = Files.readString(jsonDataLocation.toPath());
        assertEquals(expectedOutput, text);
    }
}
