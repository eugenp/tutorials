package com.baeldung.string;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class GetBytesTest {

    @Test
    public void whenGetBytes_thenCorrect() {
        byte[] byteArray = "abcd".getBytes();
        byte[] expected = new byte[] { 97, 98, 99, 100 };
        assertArrayEquals(expected, byteArray);
    }
}
