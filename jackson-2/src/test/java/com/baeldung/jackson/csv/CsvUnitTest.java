package com.baeldung.jackson.csv;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.io.Files;


public class CsvUnitTest {

    @Test
    public void givenJsonInput_thenWriteCsv() throws JsonParseException, JsonMappingException, IOException {
        JsonCsvConverter.JsonToCsv(new File("src/main/resources/orderLines.json"), 
            new File("src/main/resources/csvFromJson.csv"));
        
        assertEquals(readFile("src/main/resources/csvFromJson.csv"),
            readFile("src/test/resources/expectedCsvFromJson.csv"));
    }
    
    @Test
    public void givenCsvInput_thenWritesJson() throws JsonParseException, JsonMappingException, IOException {
        JsonCsvConverter.csvToJson(new File("src/main/resources/orderLines.csv"), 
            new File("src/main/resources/jsonFromCsv.json"));
        
        assertEquals(readFile("src/main/resources/jsonFromCsv.json"),
            readFile("src/test/resources/expectedJsonFromCsv.json"));
        
    }
    
    @Test
    public void givenJsonInput_thenWriteFormattedCsvOutput() throws JsonParseException, JsonMappingException, IOException {
        JsonCsvConverter.JsonToFormattedCsv(new File("src/main/resources/orderLines.json"), 
            new File("src/main/resources/formattedCsvFromJson.csv"));

        assertEquals(readFile("src/main/resources/formattedCsvFromJson.csv"),
            readFile("src/test/resources/expectedFormattedCsvFromJson.csv"));
        
    }
    
    private List<String> readFile(String filename) throws IOException {
        return Files.readLines(new File(filename), Charset.forName("utf-8"));
    }
    
    
}
;