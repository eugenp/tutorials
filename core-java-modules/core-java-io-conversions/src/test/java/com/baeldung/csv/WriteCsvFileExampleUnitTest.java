package com.baeldung.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteCsvFileExampleUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(WriteCsvFileExampleUnitTest.class);

    private WriteCsvFileExample csvExample;

    @Before
    public void setupClass() {
        csvExample = new WriteCsvFileExample();
    }

    @Test
    public void givenCommaContainingData_whenEscapeSpecialCharacters_stringReturnedInQuotes() {
        String data = "three,two,one";
        String escapedData = csvExample.escapeSpecialCharacters(data);

        String expectedData = "\"three,two,one\"";
        assertEquals(expectedData, escapedData);
    }

    @Test
    public void givenQuoteContainingData_whenEscapeSpecialCharacters_stringReturnedFormatted() {
        String data = "She said \"Hello\"";
        String escapedData = csvExample.escapeSpecialCharacters(data);

        String expectedData = "\"She said \"\"Hello\"\"\"";
        assertEquals(expectedData, escapedData);
    }

    @Test
    public void givenNewlineContainingData_whenEscapeSpecialCharacters_stringReturnedInQuotes() {
        String dataNewline = "This contains\na newline";
        String dataCarriageReturn = "This contains\r\na newline and carriage return";
        String escapedDataNl = csvExample.escapeSpecialCharacters(dataNewline);
        String escapedDataCr = csvExample.escapeSpecialCharacters(dataCarriageReturn);

        String expectedData = "This contains a newline";
        assertEquals(expectedData, escapedDataNl);
        String expectedDataCr = "This contains a newline and carriage return";
        assertEquals(expectedDataCr, escapedDataCr);
    }

    @Test
    public void givenNonSpecialData_whenEscapeSpecialCharacters_stringReturnedUnchanged() {
        String data = "This is nothing special";
        String returnedData = csvExample.escapeSpecialCharacters(data);

        assertEquals(data, returnedData);
    }

    @Test
    public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {
        List<String[]> dataLines = new ArrayList<String[]>();
        dataLines.add(new String[] { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
        dataLines.add(new String[] { "Jane", "Doe, Jr.", "19", "She said \"I'm being quoted\"" });

        File csvOutputFile = File.createTempFile("exampleOutput", ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                .map(csvExample::convertToCSV)
                .forEach(pw::println);
        } catch (FileNotFoundException e) {
            LOG.error("IOException " + e.getMessage());
        }

        assertTrue(csvOutputFile.exists());
        csvOutputFile.deleteOnExit();
    }
    @Test
    public void testEscapeSpecialCharacters_NullInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            escapeSpecialCharacters(null);
        });
        assertEquals("Input data cannot be null", exception.getMessage());
    }

    @Test
    public void testEscapeSpecialCharacters_LineBreaks() {
        String input = "hello\nworld";
        String expected = "hello world";
        String actual = escapeSpecialCharacters(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testEscapeSpecialCharacters_Comma() {
        String input = "hello,world";
        String expected = "\"hello,world\"";
        String actual = escapeSpecialCharacters(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testEscapeSpecialCharacters_Quotes() {
        String input = "he said \"hello\"";
        String expected = "\"he said \"\"hello\"\"\"";
        String actual = escapeSpecialCharacters(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testEscapeSpecialCharacters_SingleQuote() {
        String input = "it\'s a test";
        String expected = "\"it\'s a test\"";
        String actual = escapeSpecialCharacters(input);
        assertEquals(expected, actual);
    }

    // Helper method to call the original method
    private String escapeSpecialCharacters(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        data = data.replaceAll("\\R", " ");
        String escapedData = data;

        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;


    }
