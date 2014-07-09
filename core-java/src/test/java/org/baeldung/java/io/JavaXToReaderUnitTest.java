package org.baeldung.java.io;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.input.CharSequenceReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
