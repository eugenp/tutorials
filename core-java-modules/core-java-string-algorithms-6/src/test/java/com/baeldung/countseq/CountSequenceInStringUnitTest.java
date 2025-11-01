package com.baeldung.countseq;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class CountSequenceInStringUnitTest {

    private final static String INPUT = "This is a test string. This test is for testing the count of a sequence in a string. This string has three sentences.";

    int countSeqByIndexOf(String input, String seq) {
        int count = 0;
        int index = input.indexOf(seq);
        while (index != -1) {
            count++;
            index = input.indexOf(seq, index + seq.length());
        }
        return count;
    }

    @Test
    void whenUsingIndexOf_thenCorrect() {
        assertEquals(3, countSeqByIndexOf(INPUT, "string"));
        assertEquals(2, countSeqByIndexOf(INPUT, "string."));
    }

    int countSeqByRegexFind(String input, String seq) {
        // Alternative: Pattern pattern = Pattern.compile(seq, Pattern.LITERAL);
        Matcher matcher = Pattern.compile(Pattern.quote(seq))
            .matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    @Test
    void whenUsingRegexFind_thenCorrect() {
        assertEquals(3, countSeqByRegexFind(INPUT, "string"));
        assertEquals(2, countSeqByRegexFind(INPUT, "string."));
    }

    int countSeqByRegexSplit(String input, String seq) {
        Pattern pattern = Pattern.compile(seq, Pattern.LITERAL);
        return pattern.split(input, -1).length - 1;
    }

    @Test
    void whenUsingRegexSplit_thenCorrect() {
        assertEquals(3, countSeqByRegexSplit(INPUT, "string"));
        assertEquals(2, countSeqByRegexSplit(INPUT, "string."));
    }

    int countSeqByStream(String input, String seq) {
        long count = Pattern.compile(Pattern.quote(seq))
            .matcher(input)
            .results()
            .count();
        return Math.toIntExact(count);
    }

    @Test
    void whenUsingStream_thenCorrect() {
        assertEquals(3, countSeqByStream(INPUT, "string"));
        assertEquals(2, countSeqByStream(INPUT, "string."));
    }

    @Test
    void whenUsingApacheCommonsLangCountMatches_thenCorrect() {
        assertEquals(3, StringUtils.countMatches(INPUT, "string"));
        assertEquals(2, StringUtils.countMatches(INPUT, "string."));
    }
}