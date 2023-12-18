package com.baeldung.writestr;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WriteStringToOutputStreamUnitTest {

    private static final String UTF8_STRING = "Hello";

    private static final byte[] UTF8_BYTES = UTF8_STRING.getBytes(StandardCharsets.UTF_8);

    private static ByteArrayOutputStream outputStream;

    @BeforeEach
    void setup() {
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    void whenWriteEncodedBytesToOutputStream_thenBytesAreEqual() throws IOException {
        outputStream.write(UTF8_BYTES);
        assertThat(outputStream.toByteArray()).isEqualTo(UTF8_BYTES);
    }

    @Test
    void whenWriteStringToOutputStreamWriter_thenBytesAreEqual() throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            writer.write(UTF8_STRING);
        }

        assertThat(outputStream.toByteArray()).isEqualTo(UTF8_BYTES);
    }

    @Test
    void whenWriteStringToPrintStreamWithEncodingSpecified_thenBytesAreEqual() {
        try (PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8)) {
            printStream.print(UTF8_STRING);
        }

        assertThat(outputStream.toByteArray()).isEqualTo(UTF8_BYTES);
    }

    @Test
    void whenWriteStringToPrintStream_thenBytesAreEqual() {
        try (PrintStream printStream = new PrintStream(outputStream)) {
            printStream.print(UTF8_STRING);
        }

        assertThat(outputStream.toByteArray()).isEqualTo(UTF8_BYTES);
    }

    @Test
    void whenWriteCharArrayToPrintWriter_thenStringsAreEqual() {
        char[] c = new char[] {'H', 'e', 'l' ,'l', 'o'};
        Writer stringWriter = new StringWriter();

        try (PrintWriter writer = new PrintWriter(stringWriter)) {
            writer.write(c);
        }

        assertThat(stringWriter.toString()).isEqualTo(UTF8_STRING);
    }

}