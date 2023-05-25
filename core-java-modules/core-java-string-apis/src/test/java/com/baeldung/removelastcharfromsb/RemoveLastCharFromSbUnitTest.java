package com.baeldung.removelastcharfromsb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RemoveLastCharFromSbUnitTest {
    @Test
    void givenSb_whenRemovingUsingDeleteCharAt_shouldGetExpectedResult() {
        StringBuilder sb = new StringBuilder("Using the sb.deleteCharAt() method!");
        sb.deleteCharAt(sb.length() - 1);
        assertEquals("Using the sb.deleteCharAt() method", sb.toString());
    }

    @Test
    void givenSb_whenRemovingUsingReplace_shouldGetExpectedResult() {
        StringBuilder sb = new StringBuilder("Using the sb.replace() method!");
        int last = sb.length() - 1;
        sb.replace(last, last + 1, "");
        assertEquals("Using the sb.replace() method", sb.toString());
    }

    @Test
    void givenSb_whenRemovingUsingSubString_shouldGetExpectedResult() {
        StringBuilder sb = new StringBuilder("Using the sb.substring() method!");
        assertEquals("Using the sb.substring() method", sb.substring(0, sb.length() - 1));
        //the stringBuilder object is not changed
        assertEquals("Using the sb.substring() method!", sb.toString());
    }
}
