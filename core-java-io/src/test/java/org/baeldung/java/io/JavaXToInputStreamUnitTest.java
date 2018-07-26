package org.baeldung.java.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

public class JavaXToInputStreamUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // tests - String - InputStream

    @Test
    public final void givenUsingPlainJava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingGuava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = new ReaderInputStream(CharSource.wrap(initialString).openStream());

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = IOUtils.toInputStream(initialString);

        IOUtils.closeQuietly(targetStream);
    }

    // byte array - InputStream

    @Test
    public final void givenUsingPlainJava_whenConvertingByteArrayToInputStream_thenCorrect() throws IOException {
        final byte[] initialArray = { 0, 1, 2 };
        final InputStream targetStream = new ByteArrayInputStream(initialArray);

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingGuava_whenConvertingByteArrayToInputStream_thenCorrect() throws IOException {
        final byte[] initialArray = { 0, 1, 2 };
        final InputStream targetStream = ByteSource.wrap(initialArray).openStream();

        IOUtils.closeQuietly(targetStream);
    }

    // File - InputStream

    @Test
    public final void givenUsingPlainJava_whenConvertingFileToInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final InputStream targetStream = new FileInputStream(initialFile);

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingGuava_whenConvertingFileToInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final InputStream targetStream = Files.asByteSource(initialFile).openStream();

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingFileToInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final InputStream targetStream = FileUtils.openInputStream(initialFile);

        IOUtils.closeQuietly(targetStream);
    }

}
