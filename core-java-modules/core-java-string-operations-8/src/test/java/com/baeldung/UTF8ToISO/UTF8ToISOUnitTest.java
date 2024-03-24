package com.baeldung.UTF8ToISO;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;

public class UTF8ToISOUnitTest {
    String string = "âabcd";
    byte[] expectedBytes = new byte[]{(byte) 0xE2, 0x61, 0x62, 0x63, 0x64};

    @Test
    public void givenUtf8String_whenUsingGetByte_thenIsoBytesShouldBeEqual() {
        byte[] iso88591bytes = string.getBytes(StandardCharsets.ISO_8859_1);
        assertArrayEquals(expectedBytes, iso88591bytes);
    }

    @Test
    public void givenString_whenUsingByteBufferCharBufferConvertToIso_thenBytesShouldBeEqual() {
        ByteBuffer inputBuffer = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
        CharBuffer data = StandardCharsets.UTF_8.decode(inputBuffer);
        ByteBuffer outputBuffer = StandardCharsets.ISO_8859_1.encode(data);
        byte[] outputData = new byte[outputBuffer.remaining()];
        outputBuffer.get(outputData);
        assertArrayEquals(expectedBytes, outputData);
    }
}
