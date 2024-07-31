package com.baeldung.usinggzipInputstream;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ReadingGZIPUsingGZIPInputStreamUnitTest {
    String testFilePath = Objects.requireNonNull(ReadingGZIPUsingGZIPInputStreamUnitTest.class.getClassLoader().getResource("myFile.gz")).getFile();
    List<String> expectedFilteredLines = Arrays.asList("Line 1 content", "Line 2 content", "Line 3 content");

    @Test
    void givenGZFile_whenUsingGZIPInputStream_thenReadLines() throws IOException {
        try (Stream<String> lines = Main.readGZipFile(testFilePath).stream()) {
            List<String> result = lines
                    .filter(expectedFilteredLines::contains)
                    .collect(Collectors.toList());

            assertEquals(expectedFilteredLines, result);
        }
    }

    @Test
    void givenGZFile_whenUsingtestFindInZipFile_thenReadLines() throws IOException {
        String toFind = "Line 1 content";

        List<String> result = Main.findInZipFile(testFilePath, toFind);

        assertEquals("Line 1 content", result.get(0));

    }

    @Test
    void givenGZFile_whenUsingContentsOfZipFile_thenReadLines() throws IOException {
        AtomicInteger count = new AtomicInteger(0);

        Main.useContentsOfZipFile(testFilePath, linesStream -> {
            linesStream.filter(line -> line.length() > 10).forEach(line -> count.incrementAndGet());
        });

        assertEquals(3, count.get());
    }
}
