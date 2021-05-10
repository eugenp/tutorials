package com.baeldung.ignore.pattern.metacharacters;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class IgnoringPatternMetacharactersUnitTest {
    private static final String dollarAmounts = "$100.25, $100.50, $150.50, $100.50, $100.75";
    private static final String patternStr = "$100.50";

    @Test
    public void givenPatternStringHasMetacharacters_whenPatternMatchedWithoutEscapingMetacharacters_thenNoMatchesFound() {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(dollarAmounts);

        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(0, matches);
    }

    @Test
    public void givenPatternStringHasMetacharacters_whenPatternCompiledUsingManuallyMetaEscapedPattern_thenMatchingSuccessful() {
        String metaEscapedPatternStr = "\\Q" + patternStr + "\\E";
        Pattern pattern = Pattern.compile(metaEscapedPatternStr);
        Matcher matcher = pattern.matcher(dollarAmounts);

        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(2, matches);
    }

    @Test
    public void givenPatternStringHasMetacharacters_whenPatternCompiledUsingLiteralPatternFromQuote_thenMatchingSuccessful() {
        String literalPatternStr = Pattern.quote(patternStr);
        Pattern pattern = Pattern.compile(literalPatternStr);
        Matcher matcher = pattern.matcher(dollarAmounts);

        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(2, matches);
    }
}
