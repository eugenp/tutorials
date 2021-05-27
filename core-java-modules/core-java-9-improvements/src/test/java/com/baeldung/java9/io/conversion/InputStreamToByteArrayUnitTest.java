package com.baeldung.java9.io.conversion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

public class InputStreamToByteArrayUnitTest {

    @Test
    public final void givenUsingPlainJavaOnFixedSizeStream_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        final InputStream is = new ByteArrayInputStream(new byte[] { 0, 1, 2 });
        final byte[] targetArray = new byte[is.available()];

        is.read(targetArray);
    }

    @Test
    public final void givenUsingPlainJavaOnUnknownSizeStream_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        final InputStream is = new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4, 5, 6 });
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        final byte[] data = new byte[4];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        final byte[] targetArray = buffer.toByteArray();
    }

    @Test
    public final void givenUsingPlainJava9OnUnknownSizeStream_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        final InputStream is = new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4, 5, 6 });
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        final byte[] data = new byte[4];

        while ((nRead = is.readNBytes(data, 0, data.length)) != 0) {
            System.out.println("here " + nRead);
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        final byte[] targetArray = buffer.toByteArray();
    }

    @Test
    public void givenUsingPlainJava9_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        final InputStream is = new ByteArrayInputStream(new byte[] { 0, 1, 2 });

        byte[] data = is.readAllBytes();
    }

    @Test
    public final void givenUsingGuava_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        final InputStream initialStream = ByteSource.wrap(new byte[] { 0, 1, 2 })
            .openStream();
        final byte[] targetArray = ByteStreams.toByteArray(initialStream);
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        final InputStream initialStream = new ByteArrayInputStream(new byte[] { 0, 1, 2 });
        final byte[] targetArray = IOUtils.toByteArray(initialStream);
    }
}
