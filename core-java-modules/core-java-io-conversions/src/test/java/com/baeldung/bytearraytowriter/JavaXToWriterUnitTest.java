package com.baeldung.bytearraytowriter;

import com.google.common.io.CharSink;
import org.apache.commons.io.output.StringBuilderWriter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

public class JavaXToWriterUnitTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // tests - byte[] to Writer

    @Test
    public void givenPlainJava_whenConvertingByteArrayIntoWriter_thenCorrect() throws IOException {
        final byte[] initialArray = "With Java".getBytes();

        final Writer targetWriter = new StringWriter().append(new String(initialArray));

        targetWriter.close();

        assertEquals("With Java", targetWriter.toString());
    }

    @Test
    public void givenUsingGuava_whenConvertingByteArrayIntoWriter_thenCorrect() throws IOException {
        final byte[] initialArray = "With Guava".getBytes();

        final String buffer = new String(initialArray);
        final StringWriter stringWriter = new StringWriter();
        final CharSink charSink = new CharSink() {
            @Override
            public final Writer openStream() throws IOException {
                return stringWriter;
            }
        };
        charSink.write(buffer);

        stringWriter.close();

        assertEquals("With Guava", stringWriter.toString());
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingByteArrayIntoWriter_thenCorrect() throws IOException {
        final byte[] initialArray = "With Commons IO".getBytes();
        final Writer targetWriter = new StringBuilderWriter(new StringBuilder(new String(initialArray)));

        targetWriter.close();

        assertEquals("With Commons IO", targetWriter.toString());
    }

}
