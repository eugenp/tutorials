package com.baeldung.capitalizethefirstletter;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class CapitalizeTheFirstLetterUnitTest {
    private static final String EMPTY_INPUT = "";
    private static final String EMPTY_EXPECTED = "";
    private static final String INPUT = "hi there, Nice to Meet You!";
    private static final String EXPECTED = "Hi there, Nice to Meet You!";

    @Test
    void givenString_whenCapitalizeUsingSubString_shouldGetExpectedResult() {
        String output = INPUT.substring(0, 1).toUpperCase() + INPUT.substring(1);
        assertEquals(EXPECTED, output);

        assertThrows(IndexOutOfBoundsException.class, () -> EMPTY_INPUT.substring(1));

    }

    @Test
    void givenString_whenCapitalizeUsingRegexReplace_shouldGetExpectedResult() {
        String output = Pattern.compile("^.").matcher(INPUT).replaceFirst(m -> m.group().toUpperCase());
        assertEquals(EXPECTED, output);

        String emptyOutput = Pattern.compile("^.").matcher(EMPTY_INPUT).replaceFirst(m -> m.group().toUpperCase());
        assertEquals(EMPTY_EXPECTED, emptyOutput);
    }

    @Test
    void givenString_whenCapitalizeUsingApacheCommons_shouldGetExpectedResult() {
        String output = StringUtils.capitalize(INPUT);
        assertEquals(EXPECTED, output);

        String emptyOutput = StringUtils.capitalize(EMPTY_INPUT);
        assertEquals(EMPTY_EXPECTED, emptyOutput);

        String nullOutput = StringUtils.capitalize(null);
        assertNull(nullOutput);
    }
}
