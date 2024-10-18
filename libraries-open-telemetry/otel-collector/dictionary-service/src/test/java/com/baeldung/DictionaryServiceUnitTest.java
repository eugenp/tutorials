package com.baeldung;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DictionaryServiceUnitTest {
    private DictionaryService dictionaryService;

    @BeforeEach
    void setUp() {
        dictionaryService = new DictionaryService();
    }

    @Test
    void whenGeneratingAKnownWord_thenReturnMeaning() {
        String word = "serendipity";
        String meaning = dictionaryService.getWordMeaning(word);

        assertEquals("The occurrence and development of events by chance in a happy or beneficial way.", meaning);
    }

    @Test
    void whenGeneratingAnUnknownWord_thenReturnErrorMessage() {
        String word = "unknownWord";
        String meaning = dictionaryService.getWordMeaning(word);

        assertEquals("Meaning not found.", meaning);
    }
}
