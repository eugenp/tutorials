package com.baeldung.regex.aftermatch;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetTextAfterTheRegexMatchUnitTest {
    private static final String INPUT1 = "Some text, targetValue=Regex is cool";
    private static final String INPUT2 = "Some text. targetValue=Java is cool. some other text";

    @Test
    void whenUsingSplit_thenGetExpectedString() {
        String result1 = INPUT1.split("targetValue=")[1];
        assertEquals("Regex is cool", result1);

        String afterFirstSplit = INPUT2.split("targetValue=")[1];
        assertEquals("Java is cool. some other text", afterFirstSplit);
        String result2 = afterFirstSplit.split("[.]")[0];
        assertEquals("Java is cool", result2);

        // if use the dot as the regex for splitting, the result array is empty
        String[] splitByDot = INPUT2.split("targetValue=")[1].split(".");
        assertEquals(0, splitByDot.length);
    }

    @Test
    void whenUsingReplaceAll_thenGetExpectedString() {
        String result1 = INPUT1.replaceAll(".*targetValue=", "");
        assertEquals("Regex is cool", result1);

        String afterFirstReplace = INPUT2.replaceAll(".*targetValue=", "");
        assertEquals("Java is cool. some other text", afterFirstReplace);
        String result2 = afterFirstReplace.replaceAll("[.].*", "");
        assertEquals("Java is cool", result2);

    }

    @Test
    void whenUsingRegexGrouping_thenGetExpectedString() {
        Pattern p1 = Pattern.compile("targetValue=(.*)");
        Matcher m1 = p1.matcher(INPUT1);
        assertTrue(m1.find());
        String result1 = m1.group(1);
        assertEquals("Regex is cool", result1);

        Pattern p2 = Pattern.compile("targetValue=([^.]*)");
        Matcher m2 = p2.matcher(INPUT2);
        assertTrue(m2.find());
        String result2 = m2.group(1);
        assertEquals("Java is cool", result2);

        Pattern p3 = Pattern.compile("targetValue=(.*?)[.]");
        Matcher m3 = p3.matcher(INPUT2);
        assertTrue(m3.find());
        String result3 = m3.group(1);
        assertEquals("Java is cool", result3);
    }

    @Test
    void whenUsingLookaround_thenGetExpectedString() {
        Pattern p1 = Pattern.compile("(?<=targetValue=).*");
        Matcher m1 = p1.matcher(INPUT1);
        assertTrue(m1.find());
        String result1 = m1.group();
        assertEquals("Regex is cool", result1);

        Pattern p2 = Pattern.compile("(?<=targetValue=)[^.]*");
        Matcher m2 = p2.matcher(INPUT2);
        assertTrue(m2.find());
        String result2 = m2.group();
        assertEquals("Java is cool", result2);

        Pattern p3 = Pattern.compile("(?<=targetValue=).*(?=[.])");
        Matcher m3 = p3.matcher(INPUT2);
        assertTrue(m3.find());
        String result3 = m3.group();
        assertEquals("Java is cool", result3);
    }
}