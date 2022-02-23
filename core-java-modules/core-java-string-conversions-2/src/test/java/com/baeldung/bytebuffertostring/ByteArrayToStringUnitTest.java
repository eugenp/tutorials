package com.baeldung.bytebuffertostring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class ByteArrayToStringUnitTest {
    private static Charset charset = StandardCharsets.UTF_8;
    private static final String content = "baeldung";

    @Test
    public void convertUsingNewStringFromBufferArray_thenOK() {
        // Allocate a ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
        if (byteBuffer.hasArray()) {
            String newContent = new String(byteBuffer.array(), charset);
            assertEquals(content, newContent);
        }
    }

    @Test
    public void convertUsingNewStringFromByteBufferGetBytes_thenOK() {
        // Allocate a ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        String newContent = new String(bytes, charset);
        assertEquals(content, newContent);
    }

    @Test
    public void convertUsingCharsetDecode_thenOK() {
        // Allocate a ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
        String newContent = charset.decode(byteBuffer)
            .toString();
        assertEquals(content, newContent);
    }

}
