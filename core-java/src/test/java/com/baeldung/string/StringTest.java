package com.baeldung.string;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringTest {

    @Test
    public void whenCallCodePointAt_thenDecimalUnicodeReturned() {
        assertEquals(97, "abcd".codePointAt(0));
    }

    @Test
    public void whenCallConcat_thenCorrect() {
        assertEquals("elephant", "elep".concat("hant"));
    }

    @Test
    public void whenGetBytes_thenCorrect() {
        byte[] byteArray = "abcd".getBytes();
        byte[] expected = new byte[] { 97, 98, 99, 100 };
        assertArrayEquals(expected, byteArray);
    }
}
