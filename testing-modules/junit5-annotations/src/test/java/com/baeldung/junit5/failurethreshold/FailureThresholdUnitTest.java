package com.baeldung.junit5.failurethreshold;

import org.junit.jupiter.api.RepeatedTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FailureThresholdUnitTest {

    @RepeatedTest(value = 5, failureThreshold = 1)
    void givenTextFile_whenItIsReadAndContainsSpecifiedWordRepeatedly_thenMatchingLinesFound() throws IOException {
        String filePath = "test_file.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            assertTrue(line.contains("The"));
        }
    }
}
