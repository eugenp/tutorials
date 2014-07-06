package org.baeldung.java.io;

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
    public void givenUsingPlainJava_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader("text");

        final char[] mediationArray = new char["text".length()];
        initialReader.read(mediationArray);
        initialReader.close();
        final String targetString = new String(mediationArray);
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = CharSource.wrap("Google Guava v.17.0").openStream();
        final String targetString = CharStreams.toString(initialReader);
        initialReader.close();
    }

    @Test
    public void givenUsingCommonsIo_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader("Apache Commons IO 2.4");
        final String targetString = IOUtils.toString(initialReader);
        initialReader.close();
    }

}
