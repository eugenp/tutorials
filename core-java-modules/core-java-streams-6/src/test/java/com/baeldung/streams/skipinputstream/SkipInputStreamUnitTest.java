package com.baeldung.streams.skipinputstream;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class SkipInputStreamUnitTest {
    @Test
    public void givenInputStreamWithBytes_whenSkipBytes_thenRemainingBytes() throws IOException {
        byte[] inputData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        InputStream inputStream = new ByteArrayInputStream(inputData);

        long bytesToSkip = 3;
        long skippedBytes = inputStream.skip(bytesToSkip);

        assertArrayEquals(new byte[]{4, 5, 6, 7, 8, 9, 10}, readRemainingBytes(inputStream));

        assert skippedBytes == bytesToSkip : "Incorrect number of bytes skipped";
    }

    private byte[] readRemainingBytes(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[inputStream.available()];
        int bytesRead = inputStream.read(buffer);
        if (bytesRead == -1) {
            throw new IOException("End of stream reached");
        }
        return buffer;
    }
}
