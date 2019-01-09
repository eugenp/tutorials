package com.baeldung.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteCsvFileExampleUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(WriteCsvFileExampleUnitTest.class);

    private static final String CSV_FILE_NAME = "src/test/resources/exampleOutput.csv";
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
    public void givenBufferedWriter_whenWriteLine_thenOutputCreated() {
        List<String[]> dataLines = new ArrayList<String[]>();
        dataLines.add(new String[] { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
        dataLines.add(new String[] { "Jane", "Doe, Jr.", "19", "She said \"I'm being quoted\"" });

        File csvOutputFile = new File(CSV_FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvOutputFile))) {
            for (Iterator<String[]> dataIterator = dataLines.iterator(); dataIterator.hasNext();) {
                csvExample.writeLine(writer, dataIterator.next());
                if (dataIterator.hasNext()) {
                    writer.newLine();
                }
            }
            writer.flush();
        } catch (IOException e) {
            LOG.error("IOException " + e.getMessage());
        }

        assertTrue(csvOutputFile.exists());
    }
}
