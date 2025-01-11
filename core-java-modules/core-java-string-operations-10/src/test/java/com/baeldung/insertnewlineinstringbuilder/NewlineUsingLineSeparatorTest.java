package com.baeldung.insertnewlineinstringbuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewlineUsingLineSeparatorTest {

    @Test
    public void whenUsingLineSeparator_thenCorrectNewlineIsApplied() {
        StringBuilder sb = new StringBuilder();
        sb.append("First Line");
        sb.append(System.lineSeparator());
        sb.append("Second Line");

        String expected = "First Line" + System.lineSeparator() + "Second Line";
        assertEquals(expected, sb.toString());
    }
}
