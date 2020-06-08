package com.baeldung.java9.inputstream.outputstream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.common.io.ByteStreams;

public class InputStreamToOutputStreamUnitTest {

    // buffer size used for reading and writing
    private static final int BUFFER_SIZE = 8192;

    /**
     * Reads all bytes from an input stream and writes them to an output stream.
     * @param source - input stream to copy data from
     * @param target - output stream to copy data too
     */
    private static void copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        int length;
        while ((length = source.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }

    @Test
    public final void givenUsingJavaEight_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
             ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            copy(inputStream, targetStream);
            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public final void givenUsingJavaEight_whenConvertingVeryLongStringToInputStream_thenCorrect() throws IOException {
        final String initialString = randomAlphabetic(20480);

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
             ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            copy(inputStream, targetStream);
            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public final void givenUsingJavaNine_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
             ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            inputStream.transferTo(targetStream);
            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public final void givenUsingGuava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
             ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            ByteStreams.copy(inputStream, targetStream);
            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
             ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            IOUtils.copy(inputStream, targetStream);
            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }
}
