package com.baeldung.streams.gatherer;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SentenceSplitterGathererUnitTest {

    @Test
    void givenSentences_whenUsingCustomOneToManyGatherer_thenWordsAreExtracted() {
        List<String> expectedOutput = List.of("hello", "world", "java", "streams");
        Stream<String> sentences = Stream.of("hello world", "java streams");
        List<String> words = sentences.gather(new SentenceSplitterGatherer())
            .toList();
        Assertions.assertEquals(expectedOutput, words);
    }
}
