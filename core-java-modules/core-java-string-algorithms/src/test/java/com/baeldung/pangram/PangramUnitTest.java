package com.baeldung.pangram;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PangramUnitTest {

    @Test
    public void givenValidString_isPangram_shouldReturnSuccess() {
        String input = "Two driven jocks help fax my big quiz";
        assertTrue(Pangram.isPangram(input));
        assertTrue(Pangram.isPangramWithStreams(input));
    }

    @Test
    public void givenNullString_isPangram_shouldReturnFailure() {
        String input = null;
        assertFalse(Pangram.isPangram(input));
        assertFalse(Pangram.isPangramWithStreams(input));
        assertFalse(Pangram.isPerfectPangram(input));
    }

    @Test
    public void givenPerfectPangramString_isPerfectPangram_shouldReturnSuccess() {
        String input = "abcdefghijklmNoPqrStuVwxyz";
        assertTrue(Pangram.isPerfectPangram(input));
    }

    @Test
    public void givenNonPangramString_isPangram_shouldReturnFailure() {
        String input = "invalid pangram";
        assertFalse(Pangram.isPangram(input));
        assertFalse(Pangram.isPangramWithStreams(input));
    }

    @Test
    public void givenPangram_isPerfectPangram_shouldReturnFailure() {
        String input = "Two driven jocks help fax my big quiz";
        assertFalse(Pangram.isPerfectPangram(input));
    }

}
