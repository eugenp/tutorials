package com.baeldung.string.streamtokenizer;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class StreamTokenizerDemoUnitTest {

    @Test
    public void whenStreamTokenizerWithDefaultConfigurationIsCalled_ThenCorrectTokensAreReturned() throws IOException {
        Reader reader = StreamTokenizerDemo.createReaderFromFile();
        List<Object> expectedTokens = Arrays.asList(3.0, "quick", "brown", "foxes", "jump", "over", "the", "lazy", "dog", '!', '#', "test1");

        List<Object> actualTokens = StreamTokenizerDemo.streamTokenizerWithDefaultConfiguration(reader);

        assertArrayEquals(expectedTokens.toArray(), actualTokens.toArray());
    }

    @Test
    public void whenStreamTokenizerWithCustomConfigurationIsCalled_ThenCorrectTokensAreReturned() throws IOException {
        Reader reader = StreamTokenizerDemo.createReaderFromFile();
        List<Object> expectedTokens = Arrays.asList(3.0, "quick", "brown", "foxes", "jump", "over", "the", "\"lazy\"", "dog!", '\n', '\n', '/', '/', "test2");

        List<Object> actualTokens = StreamTokenizerDemo.streamTokenizerWithCustomConfiguration(reader);

        assertArrayEquals(expectedTokens.toArray(), actualTokens.toArray());
    }

}
