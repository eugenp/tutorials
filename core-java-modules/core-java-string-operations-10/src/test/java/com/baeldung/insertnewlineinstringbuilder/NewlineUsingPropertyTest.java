package com.baeldung.insertnewlineinstringbuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewlineUsingPropertyTest {

    @Test
    public void whenUsingProperty_thenCorrectNewlineIsApplied() {
        String result = NewlineUsingProperty.getFormattedString();
        String expected = "First Line" + System.getProperty("line.separator") + "Second Line";
        assertEquals(expected, result);
    }
}
