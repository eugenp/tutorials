package com.baeldung.java.conversion;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class StringCharArrayConversionTest {

    @Test
    public void stringToCharArray() throws Exception {
        String beforeConvCharArr = "Text";
        char[] afterConvCharArr = {'T','e','x','t'};

        assertArrayEquals(beforeConvCharArr.toCharArray(),afterConvCharArr);
    }

    @Test
    public void charArrayToString() throws Exception {
        char[] beforeConvStr = {'T','e','x','t'};
        String afterConvStr = "Text";

        assertEquals(String.valueOf(beforeConvStr),afterConvStr);
    }
}
