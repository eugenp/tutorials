package com.baeldung.removetrailingspaces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class RemoveTrailingSpaceOrWhitespaceUnitTest {

    private final static String INPUT = "  a b c d e \t  ";

    @Test
    void whenUsingTrim_thenBothLeadingAndTrailingWhitespaceAreRemoved() {
        String result = INPUT.trim();
        assertEquals("a b c d e", result);
    }

    @Test
    void whenUsingReplaceAll_thenGetExpectedResult() {
        String result1 = INPUT.replaceAll(" +$", "");
        assertEquals("  a b c d e \t", result1);

        String result2 = INPUT.replaceAll("\\s+$", "");
        assertEquals("  a b c d e", result2);
    }

    @Test
    void whenUsingStripTrailing_thenAllTrailingWhitespaceRemoved() {
        String result = INPUT.stripTrailing();
        assertEquals("  a b c d e", result);
    }

    @Test
    void whenUsingStringUtilsStripEnd_thenTrailingSpaceRemoved() {
        String result = StringUtils.stripEnd(INPUT, " ");
        assertEquals("  a b c d e \t", result);
    }
}