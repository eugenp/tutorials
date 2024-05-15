package com.baeldung.regex.matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class MatcherUnitTest {

    private static final String STRING_INPUT = "test+";
    private static final String REGEX = "\\+";

    @Test
    public void whenFindFourDigitWorks_thenCorrect() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");

        assertTrue(m.find());
        assertEquals(8, m.start());
        assertEquals("2019", m.group());
        assertEquals(12, m.end());

        assertTrue(m.find());
        assertEquals(25, m.start());
        assertEquals("2020", m.group());
        assertEquals(29, m.end());

        assertFalse(m.find());
    }

    @Test
    public void givenStartIndex_whenFindFourDigitWorks_thenCorrect() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");

        assertTrue(m.find(20));
        assertEquals(25, m.start());
        assertEquals("2020", m.group());
        assertEquals(29, m.end());
    }

    @Test
    public void whenMatchFourDigitWorks_thenFail() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");
        assertFalse(m.matches());
    }

    @Test
    public void whenMatchFourDigitWorks_thenCorrect() {
        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("2019");

        assertTrue(m.matches());
        assertEquals(0, m.start());
        assertEquals("2019", m.group());
        assertEquals(4, m.end());

        assertTrue(m.matches());// matches will always return the same return
    }

    @Test
    public void whenUsingMatcher_thenReturnTrue() {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(STRING_INPUT);
        assertTrue(matcher.find());
    }

    @Test
    public void whenUsingMatches_thenReturnFalse() {
        assertFalse(Pattern.matches(REGEX, STRING_INPUT));
    }

}
