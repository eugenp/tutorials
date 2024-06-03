package com.baeldung.textbetweenparentheses;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class ExtractTextBetweenParenthesisUnitTest {

    private static final String INPUT = "a (b c) d (e f) x (y z)";
    private static final List<String> EXPECTED = List.of("b c", "e f", "y z");

    @Test
    void givenSingleOccurrenceCase_whenUsingReplaceAll_thenCorrect() {
        String myString = "a b c (d e f) x y z";

        String result = myString.replaceAll(".*[(]", "")
            .replaceAll("[)].*", "");
        assertEquals("d e f", result);
    }

    @Test
    void whenUsingGreedyRegex_thenIncorrect() {
        String myRegex = "[(](.*)[)]";
        Matcher myMatcher = Pattern.compile(myRegex)
            .matcher(INPUT);
        List<String> myResult = new ArrayList<>();
        while (myMatcher.find()) {
            myResult.add(myMatcher.group(1));
        }
        assertEquals(List.of("b c) d (e f) x (y z"), myResult);
    }

    @Test
    void whenUsingNonGreedy_thenCorrect() {
        String regex = "[(](.*?)[)]";
        List<String> result = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex)
            .matcher(INPUT);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingNegateCharClass_thenCorrect() {
        String regex = "[(]([^)]*)";
        List<String> result = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex)
            .matcher(INPUT);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingLookbehind_thenCorrect() {
        String regex = "(?<=[(])[^)]*";
        List<String> result = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex)
            .matcher(INPUT);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingStringUtils_thenCorrect() {
        String myString = "a b c (d e f) x y z";

        String result = StringUtils.substringBetween(myString, "(", ")");
        assertEquals("d e f", result);

        String[] results = StringUtils.substringsBetween(INPUT, "(", ")");
        assertArrayEquals(EXPECTED.toArray(), results);
    }
}