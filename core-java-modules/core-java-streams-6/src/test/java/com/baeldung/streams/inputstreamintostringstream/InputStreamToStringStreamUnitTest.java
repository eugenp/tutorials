package com.baeldung.streams.inputstreamtostringstream;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputStreamToStringStreamUnitTest {

    byte[] bytes = "Hello\nWorld\nThis\nis\na\ntest".getBytes(StandardCharsets.UTF_8);
    InputStream inputStream = new ByteArrayInputStream(bytes);

    @Test
    void givenInputStream_whenConvertingWithBufferedReader_thenConvertInputStreamToStringStream() throws IOException {
        try (InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {

            Stream<String> stringStream = reader.lines();

            String result = stringStream.reduce("", (s1, s2) -> s1 + s2);

            assertEquals("HelloWorldThisisatest", result);
        }
    }

    @Test
    void givenInputStream_whenConvertingWithScannerFindAll_thenConvertInputStreamToStringStream() {
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            Stream<String> stringStream = scanner.findAll(".+")
                .map(MatchResult::group);

            String result = stringStream.collect(Collectors.joining());

            assertEquals("HelloWorldThisisatest", result);
        }
    }
}
