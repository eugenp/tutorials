package com.baeldung.bytetoint;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ByteToIntConversionUnitTest {
    @Test
    public void givenByte_whenUsingTypeCasting_thenConvertToInt() {
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
    void givenByte_whenUsingMathToIntExact_thenConvertToInt() {
        byte b = -51;
        int result = ByteToIntConversion.usingMathToIntExact(b);

        assertEquals(-51, result);
    }

    @Test
    void givenByte_whenUsingByteUnsignedInt_thenConvertToInt() {
        byte b = -51;
        int result = ByteToIntConversion.usingByteUnsignedInt(b);

        assertEquals(205, result);
    }
}
