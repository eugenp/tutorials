package com.baeldung.bytetoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteToIntConversionUnitTest {
    @Test
    void givenByte_whenUsingTypeCasting_thenConvertToInt() {
        byte b = -51;
        int result = ByteToIntConversion.usingTypeCasting(b);
        assertEquals(-51, result);
    }

    @Test
    void givenByte_whenUsingIntegerValueOf_thenConvertToInt() {
        byte b = -51;
        int result = ByteToIntConversion.usingIntegerValueOf(b);
        assertEquals(-51, result);
    }

    @Test
    void givenByte_whenUsingByteIntValue_thenConvertToInt() {
        byte b = -51;
        int result = ByteToIntConversion.usingByteIntValue(b);
        assertEquals(-51, result);
    }

    @Test
    void givenByte_whenUsingBitwiseOperator_thenConvertToInt() {
        byte b = -51;
        int result = ByteToIntConversion.usingBitwiseOperator(b);
        assertEquals(205, result);
    }

    @Test
    void givenByte_whenUsingByteUnsignedInt_thenConvertToInt() {
        byte b = -51;
        int result = ByteToIntConversion.usingByteUnsignedInt(b);
        assertEquals(205, result);
    }
}
