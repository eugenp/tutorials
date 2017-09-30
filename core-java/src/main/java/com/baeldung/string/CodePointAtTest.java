package com.baeldung.string;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CodePointAtTest {

    @Test
    public void whenCallCodePointAt_thenDecimalUnicodeReturned() {
        assertEquals(97, "abcd".codePointAt(0));
    }
}
