package com.baeldung.substring;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class SubstringUnitTest {

    String text = "Julia Evans was born on 25-09-1984. She is currently living in the USA (United States of America).";

    @Test
    public void givenAString_whenUsedStringUtils_ShouldReturnProperSubstring() {
        assertEquals("United States of America", StringUtils.substringBetween(text, "(", ")"));
        assertEquals("the USA (United States of America).", StringUtils.substringAfter(text, "living in "));
        assertEquals("Julia Evans", StringUtils.substringBefore(text, " was born"));
    }

    @Test
    public void givenAString_whenUsedScanner_ShouldReturnProperSubstring() {
        try (Scanner scanner = new Scanner(text)) {
            scanner.useDelimiter("\\.");
            assertEquals("Julia Evans was born on 25-09-1984", scanner.next());
        }
    }

    @Test
    public void givenAString_whenUsedSplit_ShouldReturnProperSubstring() {
        String[] sentences = text.split("\\.");
        assertEquals("Julia Evans was born on 25-09-1984", sentences[0]);
    }

    @Test
    public void givenAString_whenUsedRegex_ShouldReturnProperSubstring() {
        Pattern pattern = Pattern.compile("\\d{2}\\-\\d{2}-\\d{4}");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            assertEquals("25-09-1984", matcher.group());
        }
    }

    @Test
    public void givenAString_whenUsedSubSequence_ShouldReturnProperSubstring() {
        assertEquals("USA (United States of America)", text.subSequence(67, text.length() - 1));
    }

    @Test
    public void givenAString_whenUsedSubstring_ShouldReturnProperSubstring() {
        assertEquals("USA (United States of America).", text.substring(67));
        assertEquals("USA (United States of America)", text.substring(67, text.length() - 1));
    }

    @Test
    public void givenAString_whenUsedSubstringWithIndexOf_ShouldReturnProperSubstring() {
        assertEquals("United States of America", text.substring(text.indexOf('(') + 1, text.indexOf(')')));
    }

    @Test
    public void givenAString_whenUsedSubstringWithLastIndexOf_ShouldReturnProperSubstring() {
        assertEquals("1984", text.substring(text.lastIndexOf('-') + 1, text.indexOf('.')));
    }

    @Test
    public void givenAString_whenUsedSubstringWithIndexOfAString_ShouldReturnProperSubstring() {
        assertEquals("USA (United States of America)", text.substring(text.indexOf("USA"), text.indexOf(')') + 1));
    }

    @Test
    public void givenAString_whenGettingTextBeforeAndAfterTheSubstringUsingIndexes_thenGetExpectedResult() {
        String substring = "was born on 25-09-1984. She ";
        int startIdx = text.indexOf(substring);
        String before = text.substring(0, startIdx);
        String after = text.substring(startIdx + substring.length());

        assertEquals("Julia Evans ", before);
        assertEquals("is currently living in the USA (United States of America).", after);
    }

    @Test
    public void givenAString_whenGettingTextBeforeAndAfterTheSubstringUsingSplit_thenGetExpectedResult() {
        String substring = "was born on 25-09-1984. She ";
        String[] result = text.split(Pattern.quote(substring));
        assertEquals(2, result.length);

        String before = result[0];
        String after = result[1];

        assertEquals("Julia Evans ", before);
        assertEquals("is currently living in the USA (United States of America).", after);
    }

    @Test
    public void givenAString_whenSplitWithRegexAndPatternQuote_thenGetDifferentResults() {
        String input = "This is an *important* issue.";
        String substring = " *important* ";
        String[] resultWithoutQuote = input.split(substring);
        assertEquals(1, resultWithoutQuote.length);
        assertEquals("This is an *important* issue.", resultWithoutQuote[0]);

        String[] result = input.split(Pattern.quote(substring));
        String before = result[0];
        String after = result[1];

        assertEquals("This is an", before);
        assertEquals("issue.", after);
    }

    @Test
    public void givenAString_whenExtractWithGreedyRegex_thenIncorrect() {
        String input = "a <%One%> b <%Two%> c <%Three%>";
        Pattern pattern = Pattern.compile("<%(.*)%>");
        Matcher matcher = pattern.matcher(input);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        assertEquals(1, result.size());
        assertEquals("One%> b <%Two%> c <%Three", result.get(0));
    }

    @Test
    public void givenAString_whenExtractWithNonGreedyRegex_thenCorrect() {
        String input = "a <%One%> b <%Two%> c <%Three%>";
        List<String> expected = List.of("One", "Two", "Three");
        Pattern pattern = Pattern.compile("<%(.*?)%>");
        Matcher matcher = pattern.matcher(input);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        assertEquals(expected, result);
    }
}