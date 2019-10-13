package com.baeldung.string.reverse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReverseStringExamplesUnitTest {

    private static final String STRING_INPUT = "cat";
    private static final String STRING_INPUT_REVERSED = "tac";
    private static final String SENTENCE = "The quick brown fox jumps over the lazy dog";
    private static final String REVERSED_WORDS_SENTENCE = "dog lazy the over jumps fox brown quick The";

    @Test
    public void whenReverseIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverse(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverse(null);
        String reversedEmpty = ReverseStringExamples.reverse(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingStringBuilderIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = ReverseStringExamples.reverseUsingStringBuilder(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverseUsingStringBuilder(null);
        String reversedEmpty = ReverseStringExamples.reverseUsingStringBuilder(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = ReverseStringExamples.reverseUsingApacheCommons(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverseUsingApacheCommons(null);
        String reversedEmpty = ReverseStringExamples.reverseUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseTheOrderOfWordsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverseTheOrderOfWords(SENTENCE);
        String reversedNull = ReverseStringExamples.reverseTheOrderOfWords(null);
        String reversedEmpty = ReverseStringExamples.reverseTheOrderOfWords(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseTheOrderOfWordsUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverseTheOrderOfWordsUsingApacheCommons(SENTENCE);
        String reversedNull = ReverseStringExamples.reverseTheOrderOfWordsUsingApacheCommons(null);
        String reversedEmpty = ReverseStringExamples.reverseTheOrderOfWordsUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertEquals(null, reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

}
