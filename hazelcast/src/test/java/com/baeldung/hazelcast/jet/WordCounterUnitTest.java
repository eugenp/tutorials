package com.baeldung.hazelcast.jet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WordCounterUnitTest {

    @Test
    public void whenGivenSentencesAndWord_ThenReturnCountOfWord() {
        List<String> sentences = new ArrayList<>();
        sentences.add("The first second was alright, but the second second was tough.");
        WordCounter wordCounter = new WordCounter();
        long countSecond = wordCounter.countWord(sentences, "second");
        assertEquals(3, countSecond);
    }

}
