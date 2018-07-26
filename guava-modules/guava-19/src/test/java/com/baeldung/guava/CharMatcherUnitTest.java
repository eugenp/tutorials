package com.baeldung.guava;

import com.google.common.base.CharMatcher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CharMatcherUnitTest {
    @Test
    public void whenMatchingLetterOrString_ShouldReturnTrueForCorrectString() throws Exception {
        String inputString = "someString789";
        boolean result = CharMatcher.javaLetterOrDigit().matchesAllOf(inputString);

        assertTrue(result);
    }

    @Test
    public void whenCollapsingString_ShouldReturnStringWithDashesInsteadOfWhitespaces() throws Exception {
        String inputPhoneNumber = "8 123 456 123";
        String result = CharMatcher.whitespace().collapseFrom(inputPhoneNumber, '-');

        assertEquals("8-123-456-123", result);
    }

    @Test
    public void whenCountingDigitsInString_ShouldReturnActualCountOfDigits() throws Exception {
        String inputPhoneNumber = "8 123 456 123";
        int result = CharMatcher.digit().countIn(inputPhoneNumber);

        assertEquals(10, result);
    }
}
