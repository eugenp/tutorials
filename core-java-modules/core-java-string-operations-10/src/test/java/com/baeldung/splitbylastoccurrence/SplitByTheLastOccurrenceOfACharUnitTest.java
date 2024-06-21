package com.baeldung.splitbylastoccurrence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class SplitByTheLastOccurrenceOfACharUnitTest {

    private static final String INPUT1 = "a b c@e f g@x y z";
    private static final String[] EXPECTED1 = new String[] { "a b c@e f g", "x y z" };

    private static final String INPUT2 = "@a b c";
    private static final String[] EXPECTED2 = new String[] { "", "a b c" };

    private static final String INPUT3 = "a b c@";
    private static final String[] EXPECTED3 = new String[] { "a b c", "" };

    private static final String INPUT4 = "a b c";
    private static final String[] EXPECTED4 = new String[] { "a b c" };

    public String[] splitByLastOccurrence(String input, char character) {
        int idx = input.lastIndexOf(character);
        if (idx < 0) {
            return new String[] { input };
        }
        return new String[] { input.substring(0, idx), input.substring(idx + 1) };
    }

    @Test
    void whenUsingSplitByLastOccurrence_thenCorrect() {
        String[] result1 = splitByLastOccurrence(INPUT1, '@');
        assertArrayEquals(EXPECTED1, result1);

        String[] result2 = splitByLastOccurrence(INPUT2, '@');
        assertArrayEquals(EXPECTED2, result2);

        String[] result3 = splitByLastOccurrence(INPUT3, '@');
        assertArrayEquals(EXPECTED3, result3);

        String[] result4 = splitByLastOccurrence(INPUT4, '@');
        assertArrayEquals(EXPECTED4, result4);
    }

    @Test
    void whenUsingSplitWithDefaultLimit_thenNotWorkingForInput3() {
        String regex = "@(?=[^@]*$)";

        String[] result1 = INPUT1.split(regex);
        assertArrayEquals(EXPECTED1, result1);

        String[] result2 = INPUT2.split(regex);
        assertArrayEquals(EXPECTED2, result2);

        String[] result3 = INPUT3.split(regex);
        assertArrayEquals(new String[] { "a b c" }, result3);

        String[] result4 = INPUT4.split(regex);
        assertArrayEquals(EXPECTED4, result4);
    }

    @Test
    void whenUsingSplitWithLimit_thenCorrect() {
        String regex = "@(?=[^@]*$)";

        String[] result1 = INPUT1.split(regex, 2);
        assertArrayEquals(EXPECTED1, result1);

        String[] result2 = INPUT2.split(regex, 2);
        assertArrayEquals(EXPECTED2, result2);

        String[] result3 = INPUT3.split(regex, 2);
        assertArrayEquals(EXPECTED3, result3);

        String[] result4 = INPUT4.split(regex, 2);
        assertArrayEquals(EXPECTED4, result4);

    }
}