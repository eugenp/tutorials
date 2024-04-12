package com.baeldung.unicode;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class UnicodeLetterCheckerUnitTest {
    @Test
    public void givenString_whenUsingIsLetter_thenReturnTrue() {
        UnicodeLetterChecker checker = new UnicodeLetterChecker();

        boolean isUnicodeLetter = checker.characterClassCheck("HelloWorld");
        assertTrue(isUnicodeLetter);
    }

    @Test
    public void givenString_whenUsingRegex_thenReturnTrue() {
        UnicodeLetterChecker checker = new UnicodeLetterChecker();

        boolean isUnicodeLetter = checker.regexCheck("HelloWorld");
        assertTrue(isUnicodeLetter);
    }

    @Test
    public void givenString_whenUsingIsAlpha_thenReturnTrue() {
        UnicodeLetterChecker checker = new UnicodeLetterChecker();

        boolean isUnicodeLetter = checker.isAlphaCheck("HelloWorld");
        assertTrue(isUnicodeLetter);
    }

    @Test
    public void givenString_whenUsingStreams_thenReturnTrue() {
        UnicodeLetterChecker checker = new UnicodeLetterChecker();

        boolean isUnicodeLetter = checker.StreamsCheck("HelloWorld");
        assertTrue(isUnicodeLetter);
    }
}
