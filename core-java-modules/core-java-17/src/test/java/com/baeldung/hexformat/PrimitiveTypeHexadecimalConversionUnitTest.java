package com.baeldung.hexformat;

import java.util.HexFormat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimitiveTypeHexadecimalConversionUnitTest {

    private HexFormat hexFormat = HexFormat.of();

    @Test
    void givenInitialisedHexFormat_whenPrimitiveByteIsPassed_thenHexStringRepresentationIsReturned() {
        String fromByte = hexFormat.toHexDigits((byte)64);
        assertEquals("40", fromByte);
    }

    @Test
    void givenInitialisedHexFormat_whenPrimitiveLongIsPassed_thenHexStringRepresentationIsReturned() {
        String fromLong = hexFormat.toHexDigits(1234_5678_9012_3456L);
        assertEquals("000462d53c8abac0", fromLong);
    }
}
