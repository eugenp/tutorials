package com.baeldung.inputstreamtostring;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unused")
public class JavaInputStreamToXUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int DEFAULT_SIZE = 1500000;

    // tests - InputStream to String

    @Test
    public final void givenUsingJava5_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        final StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        assertEquals(textBuilder.toString(), originalString);
    }

    @Test
    public void givenUsingJava8_whenConvertingAnInputStreamToAString_thenCorrect() {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        final String text = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))
          .lines()
          .collect(Collectors.joining("\n"));

        assertThat(text, equalTo(originalString));
    }

    @Test
    public void givenUsingJava9_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        final String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingJava7_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes()); // exampleString.getBytes(StandardCharsets.UTF_8);

        // When
        String text;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingGuava_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        final ByteSource byteSource = new ByteSource() {
            @Override
            public final InputStream openStream() throws IOException {
                return inputStream;
            }
        };

        final String text = byteSource.asCharSource(Charsets.UTF_8).read();

        assertThat(text, equalTo(originalString));
    }

    @Test
    public final void givenUsingGuavaAndJava7_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        // When
        String text;
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
    
    @Test
    public final void givenUsingTempFile_whenConvertingAnInputStreamToAString_thenCorrect() throws IOException {
        final String originalString = randomAlphabetic(DEFAULT_SIZE);
        final InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        // When
        Path tempFile = java.nio.file.Files.createTempDirectory("").resolve(UUID.randomUUID().toString() + ".tmp");
        java.nio.file.Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        String result = new String(java.nio.file.Files.readAllBytes(tempFile));

        assertThat(result, equalTo(originalString));
    }

    // tests - InputStream to File

    @Test
    public final void whenConvertingToFile_thenCorrect() throws IOException {
        final InputStream initialStream = new FileInputStream(new File("src/test/resources/sample.txt"));
        final byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        final File targetFile = new File("src/test/resources/targetFile.tmp");
        final OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);

        IOUtils.closeQuietly(initialStream);
        IOUtils.closeQuietly(outStream);
    }

    @Test
    public final void whenConvertingInProgressToFile_thenCorrect() throws IOException {
        final InputStream initialStream = new FileInputStream(new File("src/test/resources/sample.txt"));
        final File targetFile = new File("src/test/resources/targetFile.tmp");
        final OutputStream outStream = new FileOutputStream(targetFile);

        final byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = initialStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        IOUtils.closeQuietly(initialStream);
        IOUtils.closeQuietly(outStream);
    }

    @Test
    public final void whenConvertingAnInProgressInputStreamToFile_thenCorrect2() throws IOException {
        final InputStream initialStream = new FileInputStream(new File("src/test/resources/sample.txt"));
        final File targetFile = new File("src/test/resources/targetFile.tmp");

        java.nio.file.Files.copy(initialStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        IOUtils.closeQuietly(initialStream);
    }

    @Test
    public final void whenConvertingInputStreamToFile_thenCorrect3() throws IOException {
        final InputStream initialStream = new FileInputStream(new File("src/test/resources/sample.txt"));
        final byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        final File targetFile = new File("src/test/resources/targetFile.tmp");
        Files.write(buffer, targetFile);

        IOUtils.closeQuietly(initialStream);
    }

    @Test
    public final void whenConvertingInputStreamToFile_thenCorrect4() throws IOException {
        final InputStream initialStream = FileUtils.openInputStream(new File("src/test/resources/sample.txt"));

        final File targetFile = new File("src/test/resources/targetFile.tmp");

        FileUtils.copyInputStreamToFile(initialStream, targetFile);
    }

    @Test
    public final void givenUsingPlainJava_whenConvertingAnInputStreamToString_thenCorrect() throws IOException {
        String originalString = randomAlphabetic(8);
        InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        String text = new String(byteArray, StandardCharsets.UTF_8);
        assertThat(text, equalTo(originalString));
    }

}
