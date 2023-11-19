package com.baeldung.delpunctuation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class RemovePunctuationUnitTest {
    private static final String INPUT = "Its 1 W o r d (!@#$%^&*{}[];':\")<>,.";
    private static final String EXPECTED = "Its 1 W o r d ";

    private static final String UNICODE_INPUT = "3 March März 三月 březen маршировать (!@#$%^&*{}[];':\")<>,.";
    private static final String UNICODE_EXPECTED = "3 March März 三月 březen маршировать ";

    @Test
    void whenUsingCharClassRange_thenGetExceptedResult() {
        String result = INPUT.replaceAll("[^\\sa-zA-Z0-9]", "");
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingPunctuationCharClass_thenGetExceptedResult() {
        String result = INPUT.replaceAll("\\p{Punct}", "");
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenInputContainsUnicodeChars_thenGetExceptedResult() {
        String result1 = UNICODE_INPUT.replaceAll("[^\\sa-zA-Z0-9]", "");
        assertNotEquals(UNICODE_EXPECTED, result1);

        String actualResult1 = "3 March Mrz  bezen  ";
        assertEquals(actualResult1, result1);


        String result2 = UNICODE_INPUT.replaceAll("\\p{Punct}", "");
        assertEquals(UNICODE_EXPECTED, result2);

        String result3 = UNICODE_INPUT.replaceAll("[^\\s\\p{L}0-9]", "");
        assertEquals(UNICODE_EXPECTED, result3);
    }

}