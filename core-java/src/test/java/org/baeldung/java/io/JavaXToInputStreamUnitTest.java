package org.baeldung.java.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharSource;

@SuppressWarnings("unused")
public class JavaXToInputStreamUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void givenUsingPlainJava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        final byte[] buffer = new byte[targetStream.available()];
        targetStream.read(buffer);
        final String targetString = new String(buffer);
    }

    @Test
    public void givenUsingGuava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final CharSource source = CharSource.wrap(initialString);
        final Reader targetStream = source.openStream();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = IOUtils.toInputStream(initialString);
    }

}
