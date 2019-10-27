package com.baeldung.string.interview;

import static org.junit.Assert.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class StringToByteArrayUnitTest {
    @Test
    public void whenGetBytes_thenCorrect() throws UnsupportedEncodingException {
        byte[] byteArray1 = "abcd".getBytes();
        byte[] byteArray2 = "efgh".getBytes(StandardCharsets.US_ASCII);
        byte[] byteArray3 = "ijkl".getBytes("UTF-8");
        byte[] expected1 = new byte[] { 97, 98, 99, 100 };
        byte[] expected2 = new byte[] { 101, 102, 103, 104 };
        byte[] expected3 = new byte[] { 105, 106, 107, 108 };
         
        assertArrayEquals(expected1, byteArray1);
        assertArrayEquals(expected2, byteArray2);
        assertArrayEquals(expected3, byteArray3);
    }
}
