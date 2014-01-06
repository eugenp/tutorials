package org.baeldung.java;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class CoreJavaIoUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // tests - iterate lines in a file

    @Test
    public final void givenUsingGuava_whenIteratingAFile_thenCorrect() throws IOException {
        final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();
        Files.readLines(new File(path), Charsets.UTF_8);
        logMemory();
    }

    @Test
    public final void givenUsingCommonsIo_whenIteratingAFile_thenCorrect() throws IOException {
        final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();
        FileUtils.readLines(new File(path));
        logMemory();
    }

    @Test
    public final void whenStreamingThroughAFile_thenCorrect() throws IOException {
        final String path = "G:\\full\\train\\input\\" + "trainDataNegative.csv";
        // final String path = "G:\\full\\train\\input\\" + "trainDataPositive.csv";

        logMemory();

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                // System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }

        logMemory();
    }

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

    // utils

    private final void logMemory() {
        logger.info("Max Memory: {} Mb", Runtime.getRuntime().maxMemory() / 1048576);
        logger.info("Total Memory: {} Mb", Runtime.getRuntime().totalMemory() / 1048576);
        logger.info("Free Memory: {} Mb", Runtime.getRuntime().freeMemory() / 1048576);
    }

}
