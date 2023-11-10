package com.baeldung.isemptyvsisblank;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringIsEmptyVsIsBlankUnitTest {

    @Test
    public void givenString_whenCallIsEmpty_thenReturnCorrectValues(){
        String fullText = "Example text";
        String emptyText = "";
        String blankText = "  ";
        String specialCharText = "\t\n";

        assertFalse(fullText.isEmpty());
        assertTrue(emptyText.isEmpty());
        assertFalse(blankText.isEmpty());
        assertFalse(specialCharText.isEmpty());
    }

    @Test
    public void givenString_whenCallStringIsBlank_thenReturnCorrectValues(){
        String fullText = "Example text";
        String emptyText = "";
        String blankText = "  ";
        String whitespaceEscapeSequences = "\t\n\r\f ";

        assertFalse(fullText.isBlank());
        assertTrue(emptyText.isBlank());
        assertTrue(blankText.isBlank());
        assertTrue(whitespaceEscapeSequences.isBlank());
    }

    @Test
    public void givenString_whenCallStringUtilsIsBlank_thenReturnCorrectValues(){
        String fullText = "Example text";
        String emptyText = "";
        String blankText = "  ";

        assertFalse(isBlank(fullText));
        assertTrue(isBlank(emptyText));
        assertTrue(isBlank(blankText));
    }
}
