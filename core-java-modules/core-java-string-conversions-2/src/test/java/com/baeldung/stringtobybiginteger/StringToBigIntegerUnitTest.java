package com.baeldung.stringtobybiginteger;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class StringToBigIntegerUnitTest {

    @Test
    public void whenGetStringWithOutRadix_thenOK() {
        final String inputString = "878";
        BigInteger result = new BigInteger(inputString);
        assertEquals("878", result.toString());
    }

    @Test
    public void whenGetStringWithRadix_thenOK() {
        final String inputString = "290f98";
        BigInteger result = new BigInteger(inputString, 16);
        assertEquals("2690968", result.toString());
    }

    @Test(expected = NumberFormatException.class)
    public void whenGetStringWithOutRadix_thenThrowError() {
        final String inputString = "290f98";
        new BigInteger(inputString);
    }

    @Test(expected = NumberFormatException.class)
    public void whenGetStringWithRadix_thenThrowError() {
        final String inputString = "290f98";
        new BigInteger(inputString, 7);
    }

    @Test
    public void whenGetStringUsingByte_thenOk() {
        final String inputString = "290f98";
        byte[] inputStringBytes = inputString.getBytes();
        BigInteger result = new BigInteger(inputStringBytes);
        assertEquals("290f98", new String(result.toByteArray()));
    }
}
