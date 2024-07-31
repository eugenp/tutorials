package com.baeldung.mockinginputstream;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MockingInputStreamUnitTest {
    @Test
    public void givenSimpleImplementation_shouldProcessInputStream() throws IOException {
        int byteCount = processInputStream(new InputStream() {
            private final byte[] msg = "Hello World".getBytes();
            private int index = 0;
            @Override
            public int read() {
                if (index >= msg.length) {
                    return -1;
                }
                return msg[index++];
            }
        });
        assertThat(byteCount).isEqualTo(11);
    }

    @Test
    public void givenByteArrayInputStream_shouldProcessInputStream() throws IOException {
        String msg = "Hello World";
        int bytesCount = processInputStream(new ByteArrayInputStream(msg.getBytes()));
        assertThat(bytesCount).isEqualTo(11);
    }

    @Test
    public void givenFileInputStream_shouldProcessInputStream() throws IOException {
        InputStream inputStream = MockingInputStreamUnitTest.class.getResourceAsStream("/mockinginputstreams/msg.txt");
        int bytesCount = processInputStream(inputStream);
        assertThat(bytesCount).isEqualTo(11);
        inputStream.close();
    }

    @Test
    public void givenGeneratingInputStream_shouldProcessInputStream() throws IOException {
        InputStream inputStream = new GeneratingInputStream(10_000, "Hello World");
        int bytesCount = processInputStream(inputStream);
        assertThat(bytesCount).isEqualTo(10_000);
        inputStream.close();
    }

    int processInputStream(InputStream inputStream) throws IOException {
        int count = 0;
        while(inputStream.read() != -1) {
            count++;
        }
        return count;
    }
}
