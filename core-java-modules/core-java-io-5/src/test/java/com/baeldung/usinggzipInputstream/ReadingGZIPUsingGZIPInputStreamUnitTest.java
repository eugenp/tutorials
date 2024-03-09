package com.baeldung.usinggzipInputstream;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ReadingGZIPUsingGZIPInputStreamUnitTest {
    String testFilePath = Objects.requireNonNull(ReadingGZIPUsingGZIPInputStreamUnitTest.class.getClassLoader().getResource("myFile.gz")).getFile();

    @Test
    void givenGZFile_whenUsingGZIPInputStream_thenReadLines() throws IOException {
        List<String> expectedFilteredLines = Arrays.asList("Line 1 content", "Line 3 content");

        try (Stream<String> lines = Main.readGZipFile(testFilePath).stream()) {
            List<String> result = lines
                    .filter(expectedFilteredLines::contains)
                    .collect(Collectors.toList());

            assertEquals(expectedFilteredLines, result);
        }
    }

    @Test
    void givenGZFile_whenUsingProcessGZipFile_thenProcessLines() throws IOException {
        List<String> processedLines = new ArrayList<>();

        Main.LineProcessor lineProcessor = lines -> lines.forEach(processedLines::add);

        try (InputStream inputStream = ReadingGZIPUsingGZIPInputStreamUnitTest.class.getClassLoader().getResourceAsStream("myFile.gz")) {
            if (inputStream == null) {
                throw new IOException("File not found: myFile.gz");
            }

            Main.processGZipFile(inputStream, lineProcessor);
        }

        List<String> expectedLines = List.of("Line 1 content", "Line 2 content", "Line 3 content");

        assertEquals(expectedLines, processedLines);
    }
}
