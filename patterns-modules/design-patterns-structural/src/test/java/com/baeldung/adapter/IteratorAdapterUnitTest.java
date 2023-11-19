package com.baeldung.adapter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.junit.jupiter.api.Test;

class IteratorAdapterUnitTest {

    public static final String STRING_DATA = "Welcome to baeldung.com";
    private final StringTokenizer tokenizer = new StringTokenizer(STRING_DATA);
    private final Iterator<String> tokenizerAdapter = new StringTokenizerIteratorAdapter(STRING_DATA);
    private final List<String> expectedTokensForString = Arrays.asList("Welcome", "to", "baeldung.com");

    @Test
    void givenTokenizer_thenAdapterProducesCorrectResult() {
        List<String> actualTokens = new ArrayList<>();
        new IteratorAdapter(tokenizer).forEachRemaining(s -> actualTokens.add((String) s));
        assertEquals(expectedTokensForString, actualTokens);
    }

    @Test
    void givenTokenizerAdapter_thenIteratorProducesCorrectResult() {
        List<String> actualTokens = new ArrayList<>();
        tokenizerAdapter.forEachRemaining(actualTokens::add);
        assertEquals(expectedTokensForString, actualTokens);
    }


}