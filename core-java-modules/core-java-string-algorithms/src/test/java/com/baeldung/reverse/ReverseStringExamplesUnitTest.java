package com.baeldung.reverse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

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
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingStringBuilderIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = ReverseStringExamples.reverseUsingStringBuilder(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverseUsingStringBuilder(null);
        String reversedEmpty = ReverseStringExamples.reverseUsingStringBuilder(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() throws Exception {
        String reversed = ReverseStringExamples.reverseUsingApacheCommons(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverseUsingApacheCommons(null);
        String reversedEmpty = ReverseStringExamples.reverseUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseTheOrderOfWordsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverseTheOrderOfWords(SENTENCE);
        String reversedNull = ReverseStringExamples.reverseTheOrderOfWords(null);
        String reversedEmpty = ReverseStringExamples.reverseTheOrderOfWords(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseTheOrderOfWordsUsingApacheCommonsIsCalled_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverseTheOrderOfWordsUsingApacheCommons(SENTENCE);
        String reversedNull = ReverseStringExamples.reverseTheOrderOfWordsUsingApacheCommons(null);
        String reversedEmpty = ReverseStringExamples.reverseTheOrderOfWordsUsingApacheCommons(StringUtils.EMPTY);

        assertEquals(REVERSED_WORDS_SENTENCE, reversed);
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseStringUsingIntStreamRangeMethod_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverseUsingIntStreamRangeMethod(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverseUsingIntStreamRangeMethod(null);
        String reversedEmpty = ReverseStringExamples.reverseUsingIntStreamRangeMethod(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseStringUsingCharsMethod_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverseUsingCharsMethod(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverseUsingCharsMethod(null);
        String reversedEmpty = ReverseStringExamples.reverseUsingCharsMethod(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseStringUsingStreamOfMethod_ThenCorrectStringIsReturned() {
        String reversed = ReverseStringExamples.reverseUsingStreamOfMethod(STRING_INPUT);
        String reversedNull = ReverseStringExamples.reverseUsingStreamOfMethod(null);
        String reversedEmpty = ReverseStringExamples.reverseUsingStreamOfMethod(StringUtils.EMPTY);

        assertEquals(STRING_INPUT_REVERSED, reversed);
        assertNull(reversedNull);
        assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

}
