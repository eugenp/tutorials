package org.baeldung.java;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;

public class CoreJavaIoUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // tests - InputStream to String

    @Test
    public final void givenUsingJava7_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(8);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes()); // exampleString.getBytes(StandardCharsets.UTF_8);

        // When
        String text = null;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingGuavaNoEncoding_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(8);
        final InputSupplier<StringReader> readerSupplier = CharStreams.newReaderSupplier(originalString);

        // When
        final String text = CharStreams.toString(readerSupplier);

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingGuavaWithEncoding_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(8);
        final InputSupplier<Reader> readerSupplier = ByteSource.wrap(originalString.getBytes()).asCharSource(Charsets.UTF_8);

        // When
        final String text = CharStreams.toString(readerSupplier);

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingCommonsIoWithEncoding_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(8);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        // When
        final String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingCommonsIoWithEncoding2_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(8);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        // When
        final StringWriter writer = new StringWriter();
        final String encoding = StandardCharsets.UTF_8.name();
        IOUtils.copy(inputStream, writer, encoding);

        assertThat(writer.toString(), equalTo(originalString));
    }

}
