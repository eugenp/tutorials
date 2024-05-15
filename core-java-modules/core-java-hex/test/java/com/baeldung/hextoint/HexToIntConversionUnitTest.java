package com.baeldung.hextoint;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class HexToIntConversionUnitTest {

    @Test
    public void givenValidHexString_whenUsingParseInt_thenExpectCorrectDecimalValue() {
        String hexString = "0x00FF00";
        int expectedDecimalValue = 65280;

        int decimalValue = Integer.parseInt(hexString.substring(2), 16);

        assertEquals(expectedDecimalValue, decimalValue);
    }

    @Test
    public void givenValidHexString_whenUsingIntegerDecode_thenExpectCorrectDecimalValue() {
        String hexString = "0x00FF00";
        int expectedDecimalValue = 65280;

        int decimalValue = Integer.decode(hexString);

        assertEquals(expectedDecimalValue, decimalValue);
    }

    @Test
    public void givenValidHexString_whenUsingBigInteger_thenExpectCorrectDecimalValue() {
        String hexString = "0x00FF00";
        int expectedDecimalValue = 65280;

        BigInteger bigIntegerValue = new BigInteger(hexString.substring(2), 16);
        int decimalValue = bigIntegerValue.intValue();
        assertEquals(expectedDecimalValue, decimalValue);
    }


}
