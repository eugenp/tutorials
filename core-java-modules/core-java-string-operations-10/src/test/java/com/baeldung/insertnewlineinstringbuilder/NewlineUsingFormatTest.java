package com.baeldung.insertnewlineinstringbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewlineUsingFormatTest {
    @Test
    public void whenUsingLineSeparator_thenCorrectNewlineIsApplied() {
        String result = NewlineUsingFormat.getFormattedString();
        String expected = "First Line" + System.lineSeparator() + "Second Line";
        assertEquals(expected, result);
    }
}
