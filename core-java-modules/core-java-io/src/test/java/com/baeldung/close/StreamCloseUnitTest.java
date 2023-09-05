package com.baeldung.close;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class StreamCloseUnitTest {

    private static final Logger log = LoggerFactory.getLogger(StreamCloseUnitTest.class);

    @Mock
    private OutputStream wrappedOutputStream;

    @Mock
    private InputStream wrappedInputStream;

    @Test
    public void whenStreamClosedByFinally_thenIOStreamsCloseCalled() throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(wrappedInputStream);
            outputStream = new BufferedOutputStream(wrappedOutputStream);
        }
        finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (IOException ioe1) {
                log.error("Cannot close InputStream");
            }
            try {
                if (outputStream != null)
                    outputStream.close();
            }
            catch (IOException ioe2) {
                log.error("Cannot close OutputStream");
            }
        }

        verify(wrappedInputStream).close();
        verify(wrappedOutputStream).close();
    }

    @Test
    public void whenStreamClosedByCloseQuietly_thenIOStreamsCloseCalled() throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(wrappedInputStream);
            outputStream = new BufferedOutputStream(wrappedOutputStream);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

        verify(wrappedInputStream).close();
        verify(wrappedOutputStream).close();
    }

    @Test
    public void whenFinishReadOnAutoCloseInputStream_thenInputStreamsCloseCalled() throws IOException {

        // Mimic no more data in the InputStream
        when(wrappedInputStream.read(any(byte[].class))).thenReturn(-1);

        InputStream inputStream = AutoCloseInputStream.builder().setInputStream(wrappedInputStream).get();

        byte[] buffer = new byte[256];
        while (inputStream.read(buffer) != -1) {
        }

        verify(wrappedInputStream).close();
    }

    @Test
    public void whenStreamClosedByWithResources_thenIOStreamsCloseCalled() throws IOException {

        try (BufferedInputStream inputStream = new BufferedInputStream(wrappedInputStream);
            BufferedOutputStream outputStream = new BufferedOutputStream(wrappedOutputStream)) {
        }

        verify(wrappedInputStream).close();
        verify(wrappedOutputStream).close();
    }

    @Test
    public void whenStreamClosedByWithResourcesJava9_thenIOStreamsCloseCalled() throws IOException {

        InputStream inputStream = new BufferedInputStream(wrappedInputStream);
        OutputStream outputStream = new BufferedOutputStream(wrappedOutputStream);

        try (inputStream; outputStream) {
        }

        verify(wrappedInputStream).close();
        verify(wrappedOutputStream).close();
    }

}