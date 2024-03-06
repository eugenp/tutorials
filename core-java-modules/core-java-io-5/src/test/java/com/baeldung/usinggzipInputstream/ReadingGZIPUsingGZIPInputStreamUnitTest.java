package com.baeldung.usinggzipInputstream;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertEquals;

public class ReadingGZIPUsingGZIPInputStreamUnitTest {
    String testFilePath = Objects.requireNonNull(ReadingGZIPUsingGZIPInputStreamUnitTest.class.getClassLoader().getResource("myFile.gz")).getFile();


    @Test
    void givenGZFile_whenUsingGZIPInputStream_thenRetrieveList() throws IOException {
        File file = new File(testFilePath);
        List<String> lines = new ArrayList<>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(file));
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        assertEquals(List.of("Line 1 content", "Line 2 content", "Line 3 content"), lines);
    }

    @Test
    void givenGZFile_whenUsingGZIPInputStream_thenReadLines() throws IOException {
        List<String> expectedFilteredLines = Arrays.asList("Line 1 content", "Line 3 content");

        try (Stream<String> lines = Main.readGZipFile(testFilePath)) {
            List<String> result = lines
                    .filter(expectedFilteredLines::contains)
                    .collect(Collectors.toList());

            assertEquals(expectedFilteredLines, result);
        }
    }

}
