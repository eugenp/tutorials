package org.baeldung.java.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharSource;

@SuppressWarnings("unused")
public class JavaXToInputStreamUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // tests - String - InputStream

    @Test
    public final void givenUsingPlainJava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
    }

    @Test
    public final void givenUsingGuava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = new ReaderInputStream(CharSource.wrap(initialString).openStream());
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = IOUtils.toInputStream(initialString);
    }

    //

}
