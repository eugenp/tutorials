package com.baeldung.usinggzipInputstream;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class ReadingGZIPUsingGZIPInputStreamUnitTest {
    String testFilePath = Objects.requireNonNull(ReadingGZIPUsingGZIPInputStreamUnitTest.class.getClassLoader().getResource("myFile.gz")).getFile();

    @Test
    void givenGZFile_whenUsingGZIPInputStream_thenReadLines() throws IOException {
        Stream<String> expectedLines = Stream.of("Line 1 content", "Line 2 content", "Line 3 content");

        try (Stream<String> lines = Main.readGZipFile(testFilePath)) {
            assertLinesMatch(
                    expectedLines.map(line -> line + "\n"),
                    lines.map(line -> line + "\n")
            );
        }
    }
}
