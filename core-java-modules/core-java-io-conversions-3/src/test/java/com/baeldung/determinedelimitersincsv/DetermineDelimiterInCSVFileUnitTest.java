package com.baeldung.determinedelimitersincsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetermineDelimiterInCSVFileUnitTest {
    private static final char[] POSSIBLE_DELIMITERS = {',', ';', '\t', '|'};
    String csvLine = "name,age,location\nJohn,30,New York";

    @Test
    public void givenCSVLine_whenDetectingDelimiter_thenCorrectDelimiterFound() {
        Map<Character, Integer> delimiterCounts = new HashMap<>();

        for (char delimiter : POSSIBLE_DELIMITERS) {
            int count = csvLine.length() - csvLine.replace(String.valueOf(delimiter), "").length();
            delimiterCounts.put(delimiter, count);
        }

        char detectedDelimiter = delimiterCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(',');

        assertEquals(',', detectedDelimiter);
    }

    @Test
    public void givenCSVLines_whenDetectingDelimiter_thenCorrectDelimiterFound() {
        String[] lines = {
                "Location,Latitude,Longitude,Elevation(m)",
                "New York,40.7128,-74.0060,10",
                "Los Angeles,34.0522,-118.2437,71",
                "Chicago,41.8781,-87.6298,181"
        };

        char detectedDelimiter = ',';
        for (char delimiter : POSSIBLE_DELIMITERS) {
            boolean isConsistent = true;
            int columnCount = -1;

            for (String line : lines) {
                String[] fields = line.split(Pattern.quote(String.valueOf(delimiter)));
                if (columnCount == -1) {
                    columnCount = fields.length;
                } else if (fields.length != columnCount) {
                    isConsistent = false;
                    break;
                }
            }

            if (isConsistent) {
                detectedDelimiter = delimiter;
                break;
            }
        }

        assertEquals(',', detectedDelimiter);
    }

    @Test
    public void givenCSVLine_whenUsingApacheCommons_thenCorrectDelimiterFound() throws IOException {
        char detectedDelimiter = ',';

        for (char delimiter : POSSIBLE_DELIMITERS) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setDelimiter(delimiter).build();
            try (CSVParser parser = new CSVParser(new StringReader(csvLine), csvFormat)) {
                CSVRecord firstRecord = parser.iterator().next();
                if (firstRecord.size() == 3) {
                    detectedDelimiter = delimiter;
                    break;
                }
            }
        }

        assertEquals(',', detectedDelimiter);
    }

    @Test
    public void givenCSVLine_whenUsingRegex_thenCorrectDelimiterFound() {
        char detectedDelimiter = ',';

        for (char delimiter : POSSIBLE_DELIMITERS) {
            String[] fields = csvLine.split(Pattern.quote(String.valueOf(delimiter)));
            if (fields.length > 1) {
                detectedDelimiter = delimiter;
                break;
            }
        }

        assertEquals(',', detectedDelimiter);
    }
}