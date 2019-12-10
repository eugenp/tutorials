package com.baeldung.xtoreader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSource;

public class JavaXToReaderUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // tests - String to Reader

    @Test
    public void givenUsingPlainJava_whenConvertingStringIntoReader_thenCorrect() throws IOException {
        final String initialString = "With Plain Java";
        final Reader targetReader = new StringReader(initialString);

        targetReader.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingStringIntoReader_thenCorrect() throws IOException {
        final String initialString = "With Google Guava";
        final Reader targetReader = CharSource.wrap(initialString).openStream();

        targetReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingStringIntoReader_thenCorrect() throws IOException {
        final String initialString = "With Apache Commons IO";
        final Reader targetReader = new CharSequenceReader(initialString);

        targetReader.close();
    }

    // tests - byte array to Reader

    @Test
    public void givenUsingPlainJava_whenConvertingByteArrayIntoReader_thenCorrect() throws IOException {
        final byte[] initialArray = "Hello world!".getBytes();
        final Reader targetReader = new StringReader(new String(initialArray));

        targetReader.close();
    }

    @Test
    public void givenUsingPlainJava2_whenConvertingByteArrayIntoReader_thenCorrect() throws IOException {
        final byte[] initialArray = "Hello world!".getBytes();
        final Reader targetReader = new InputStreamReader(new ByteArrayInputStream(initialArray));

        targetReader.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingByteArrayIntoReader_thenCorrect() throws IOException {
        final byte[] initialArray = "With Guava".getBytes();
        final String bufferString = new String(initialArray);
        final Reader targetReader = CharSource.wrap(bufferString).openStream();

        targetReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingByteArrayIntoReader_thenCorrect() throws IOException {
        final byte[] initialArray = "With Commons IO".getBytes();
        final Reader targetReader = new CharSequenceReader(new String(initialArray));

        targetReader.close();
    }

    // tests - File to Reader

    @Test
    public void givenUsingPlainJava_whenConvertingFileIntoReader_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/initialFile.txt");
        initialFile.createNewFile();
        final Reader targetReader = new FileReader(initialFile);

        targetReader.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingFileIntoReader_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/initialFile.txt");
        com.google.common.io.Files.touch(initialFile);
        final Reader targetReader = com.google.common.io.Files.newReader(initialFile, Charset.defaultCharset());

        targetReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingFileIntoReader_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/initialFile.txt");
        FileUtils.touch(initialFile);
        FileUtils.write(initialFile, "With Commons IO");
        final byte[] buffer = FileUtils.readFileToByteArray(initialFile);
        final Reader targetReader = new CharSequenceReader(new String(buffer));

        targetReader.close();
    }

    // tests - InputStream to Reader

    @Test
    public void givenUsingPlainJava_whenConvertingInputStreamIntoReader_thenCorrect() throws IOException {
        final InputStream initialStream = new ByteArrayInputStream("With Java".getBytes());
        final Reader targetReader = new InputStreamReader(initialStream);

        targetReader.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingInputStreamIntoReader_thenCorrect() throws IOException {
        final InputStream initialStream = ByteSource.wrap("With Guava".getBytes()).openStream();
        final byte[] buffer = ByteStreams.toByteArray(initialStream);
        final Reader targetReader = CharSource.wrap(new String(buffer)).openStream();

        targetReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingInputStreamIntoReader_thenCorrect() throws IOException {
        final InputStream initialStream = IOUtils.toInputStream("With Commons IO");
        final byte[] buffer = IOUtils.toByteArray(initialStream);
        final Reader targetReader = new CharSequenceReader(new String(buffer));

        targetReader.close();
    }

}
