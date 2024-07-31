package com.baeldung.hexformat;

import java.util.HexFormat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteHexadecimalConversionUnitTest {

    private HexFormat hexFormat = HexFormat.of();

    @Test
    void givenInitialisedHexFormat_whenHexStringIsPassed_thenByteArrayRepresentationIsReturned() {
        byte[] hexBytes = hexFormat.parseHex("ABCDEF0123456789");
        assertArrayEquals(new byte[] { -85, -51, -17, 1, 35, 69, 103, -119 }, hexBytes);
    }

    @Test
    void givenInitialisedHexFormat_whenByteArrayIsPassed_thenHexStringRepresentationIsReturned() {
        String bytesAsString = hexFormat.formatHex(new byte[] { -85, -51, -17, 1, 35, 69, 103, -119});
        assertEquals("abcdef0123456789", bytesAsString);
    }
}
