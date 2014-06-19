package org.baeldung.java.io;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;

public class JavaInputStreamToXUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int DEFAULT_SIZE = 150000000;

    // private static final int DEFAULT_SIZE = 8;

    // tests - InputStream to String

    // 11s
    @Test
    public void givenUsingJava5_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        final StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        assertEquals(textBuilder.toString(), originalString);
    }

    // 8s
    @Test
    public final void givenUsingJava7_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes()); // exampleString.getBytes(StandardCharsets.UTF_8);

        // When
        String text = null;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingGuava_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        final InputSupplier<InputStream> inputSupplier = new InputSupplier<InputStream>() {
            @Override
            public final InputStream getInput() throws IOException {
                return inputStream;
            }
        };
        final InputSupplier<InputStreamReader> readerSupplier = CharStreams.newReaderSupplier(inputSupplier, Charsets.UTF_8);

        // When
        final String text = CharStreams.toString(readerSupplier);

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingGuavaAndJava7_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        // When
        String text = null;
        try (final Reader reader = new InputStreamReader(inputStream)) {
            text = CharStreams.toString(reader);
        }

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingCommonsIo_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        // When
        final String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingCommonsIoWithCopy_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        // When
        final StringWriter writer = new StringWriter();
        final String encoding = StandardCharsets.UTF_8.name();
        IOUtils.copy(inputStream, writer, encoding);

        assertThat(writer.toString(), equalTo(originalString));
    }

}
