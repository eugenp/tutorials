package com.baeldung.java9.io.conversion;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class InputStreamToStringStreamUnitTest {
    byte[] bytes = "Hello\nWorld\nThis\nis\na\ntest".getBytes();
    InputStream inputStream = new ByteArrayInputStream(bytes);

    @Test
    public void givenInputStream_whenConvertingWithBufferedReader_thenConvertInputStreamToStringStream() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Stream<String> stringStream = reader.lines();

        String result = stringStream.reduce("", (s1, s2) -> s1 + s2);

        assertEquals("HelloWorldThisisatest", result);
    }

    @Test
    public void givenInputStream_whenConvertingWithScanner_thenConvertInputStreamToStringStream() {

        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        String text = scanner.hasNext() ? scanner.next() : "";
        Stream<String> stringStream = Stream.of(text.split("\\r?\\n"));

        String result = stringStream.reduce("", (s1, s2) -> s1 + s2);

        assertEquals("HelloWorldThisisatest", result);
    }
}
