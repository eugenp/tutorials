package com.baeldung.isemptyvsisblank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringIsEmptyVsIsBlankUnitTest {

    @Test
    public void givenString_whenCallIsEmpty_thenReturnCorrectValues() {
        assertFalse("Example text".isEmpty());
        assertTrue("".isEmpty());
        assertFalse("  ".isEmpty());
        assertFalse("\t\n\r\f".isEmpty());
    }

    @Test
    public void givenString_whenCallStringIsBlank_thenReturnCorrectValues() {
        assertFalse("Example text".isBlank());
        assertTrue("".isBlank());
        assertTrue("  ".isBlank());
        assertTrue("\t\n\r\f ".isBlank());
    }
}
