package com.baeldung.jackson.csv;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.io.Files;

public class CsvUnitTest {

    private File csvFromJson;
    private File jsonFromCsv;
    private File formattedCsvFromJson;
    
    @Before
    public void setup() {
        csvFromJson = new File("src/main/resources/csv/csvFromJson.csv");
        jsonFromCsv = new File("src/main/resources/csv/jsonFromCsv.json");
        formattedCsvFromJson = new File("src/main/resources/csv/formattedCsvFromJson.csv");
    }

    @After
    public void cleanup() {
        csvFromJson.deleteOnExit();
        jsonFromCsv.deleteOnExit();
        formattedCsvFromJson.deleteOnExit();
    }

    @Test
    public void givenJsonInput_thenWriteCsv() throws JsonParseException, JsonMappingException, IOException {
        JsonCsvConverter.JsonToCsv(new File("src/main/resources/csv/orderLines.json"), csvFromJson);

        assertEquals(readFile(csvFromJson.getAbsolutePath()), readFile("src/test/resources/csv/expectedCsvFromJson.csv"));
    }

    @Test
    public void givenCsvInput_thenWritesJson() throws JsonParseException, JsonMappingException, IOException {
        JsonCsvConverter.csvToJson(new File("src/main/resources/csv/orderLines.csv"), jsonFromCsv);

        assertEquals(readFile(jsonFromCsv.getAbsolutePath()), readFile("src/test/resources/csv/expectedJsonFromCsv.json"));

    }

    @Test
    public void givenJsonInput_thenWriteFormattedCsvOutput() throws JsonParseException, JsonMappingException, IOException {
        JsonCsvConverter.JsonToFormattedCsv(new File("src/main/resources/csv/orderLines.json"), formattedCsvFromJson);

        assertEquals(readFile(formattedCsvFromJson.getAbsolutePath()), readFile("src/test/resources/csv/expectedFormattedCsvFromJson.csv"));

    }

    private List<String> readFile(String filename) throws IOException {
        return Files.readLines(new File(filename), Charset.forName("utf-8"));
    }

};