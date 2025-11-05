package com.baeldung.insertnewlineinstringbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewlineUsingNTest {

    @Test
    public void whenUsingN_thenCorrectNewlineIsApplied() {
        String result = NewlineUsingN.getFormattedString();
        String expected = "Line 1" + "\n" + "Line 2";
        assertEquals(expected, result);
    }
}
