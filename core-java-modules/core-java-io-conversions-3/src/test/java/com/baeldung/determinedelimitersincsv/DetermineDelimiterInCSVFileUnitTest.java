package com.baeldung.determinedelimitersincsv;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetermineDelimiterInCSVFileUnitTest {
    String[] lines = {
            "Location,Latitude,Longitude,Elevation(m)",
            "New York,40.7128,-74.0060,10",
            "Los Angeles,34.0522,-118.2437,71",
            "Chicago,41.8781,-87.6298,181"
    };

    @Test
    public void givenCSVLines_whenDetectingDelimiterUsingFrequencyCount_thenCorrectDelimiterFound() {
        char[] POSSIBLE_DELIMITERS = {',', ';', '\t', '|'};
        Map<Character, Integer> delimiterCounts = new HashMap<>();

        for (char delimiter : POSSIBLE_DELIMITERS) {
            int count = 0;
            for (String line : lines) {
                count += line.length() - line.replace(String.valueOf(delimiter), "").length();
            }
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
        char detectedDelimiter = ',';
        for (char delimiter : lines[0].toCharArray()) {
            boolean allRowsHaveEqualColumnCounts = Arrays.stream(lines)
                    .map(line -> line.split(Pattern.quote(String.valueOf(delimiter))))
                    .map(columns -> columns.length)
                    .distinct()
                    .count() == 1;

            if (allRowsHaveEqualColumnCounts) {
                detectedDelimiter = delimiter;
                break;
            }
        }

        assertEquals(',', detectedDelimiter);
    }
}
