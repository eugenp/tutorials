package com.baeldung.string.interview;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringCountOccurrencesUnitTest {
    public int countOccurrences(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    @Test
    public void givenString_whenCountingFrequencyOfChar_thenCountCorrect() {
        assertEquals(3, countOccurrences("united states", 't'));
    }

    public void givenString_whenUsingJava8_thenCountingOfCharCorrect() {
        String str = "united states";
        long count = str.chars().filter(ch -> (char)ch == 't').count();
        assertEquals(3, count);
    }
}
