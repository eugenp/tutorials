package com.baeldung.readertox;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;


@SuppressWarnings("unused")
public class JavaReaderToXUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int DEFAULT_SIZE = 1500000;

    // tests - Reader to String

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoStringV1_thenCorrect() throws IOException {
        final Reader reader = new StringReader("With Java 1");
        int intValueOfChar;
        String targetString = "";
        while ((intValueOfChar = reader.read()) != -1) {
            targetString += (char) intValueOfChar;
        }
        reader.close();
    }

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoStringV2_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader("With Java 1");
        final char[] arr = new char[8 * 1024];
        final StringBuilder buffer = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(arr, 0, arr.length)) != -1) {
            buffer.append(arr, 0, numCharsRead);
        }
        initialReader.close();
        final String targetString = buffer.toString();
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = CharSource.wrap("With Google Guava").openStream();
        final String targetString = CharStreams.toString(initialReader);
        initialReader.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader("With Apache Commons");
        final String targetString = IOUtils.toString(initialReader);
        initialReader.close();
    }

}
