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
        String seq = "string";
        int count = countSeqByIndexOf(INPUT, seq);
        assertEquals(3, count);

        String seq2 = "string.";
        int count2 = countSeqByIndexOf(INPUT, seq2);
        assertEquals(2, count2);
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
        String seq = "string";
        int count = countSeqByRegexFind(INPUT, seq);
        assertEquals(3, count);

        String seq2 = "string.";
        int count2 = countSeqByRegexFind(INPUT, seq2);
        assertEquals(2, count2);
    }

    int countSeqByRegexSplit(String input, String seq) {
        Pattern pattern = Pattern.compile(seq, Pattern.LITERAL);
        return pattern.split(input, -1).length - 1;
    }

    @Test
    void whenUsingRegexSplit_thenCorrect() {
        String seq = "string";
        int count = countSeqByRegexSplit(INPUT, seq);
        assertEquals(3, count);

        String seq2 = "string.";
        int count2 = countSeqByRegexSplit(INPUT, seq2);
        assertEquals(2, count2);
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
        String seq = "string";
        int count = countSeqByStream(INPUT, seq);
        assertEquals(3, count);

        String seq2 = "string.";
        int count2 = countSeqByStream(INPUT, seq2);
        assertEquals(2, count2);
    }

    @Test
    void whenUsingApacheCommonsLangCountMatches_thenCorrect() {
        String seq = "string";
        int count = StringUtils.countMatches(INPUT, seq);
        assertEquals(3, count);

        String seq2 = "string.";
        int count2 = StringUtils.countMatches(INPUT, seq2);
        assertEquals(2, count2);
    }
}