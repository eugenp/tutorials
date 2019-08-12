package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapters.PortugueseTranslationPackAdapter;
import com.baeldung.architecture.hexagonal.adapters.SpanishTranslationPackAdapter;
import com.baeldung.architecture.hexagonal.domain.TranslationPack;
import com.baeldung.architecture.hexagonal.domain.Translator;
import com.baeldung.architecture.hexagonal.domain.TranslatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HexagonalArchitectureUnitTest {

    private static final String UNKNOWN_WORD_TO_TRANSLATE = "bla bla bla";
    private static final String WORD_TO_TRANSLATE = "marathoners";
    private static final String WORD_TRANSLATED_TO_PORTUGUESE = "maratonistas";
    private static final String WORD_TRANSLATED_TO_SPANISH = "corredores de maratón";

    @Test
    void givenThePortugueseTranslator_whenReceiveAValidAndKnownEnglishWord_thenTranslateToPortuguese() {
        TranslationPack portugueseTranslator = new PortugueseTranslationPackAdapter();
        String wordTranslatedToPortuguese = portugueseTranslator.translate(WORD_TO_TRANSLATE);

        assertEquals(WORD_TRANSLATED_TO_PORTUGUESE, wordTranslatedToPortuguese);
    }

    @Test
    void givenThePortugueseTranslator_whenReceiveAnUnknownEnglishWord_thenReturnAnException() {
        TranslationPack portugueseTranslator = new PortugueseTranslationPackAdapter();

        assertThrows(RuntimeException.class, () -> portugueseTranslator.translate(UNKNOWN_WORD_TO_TRANSLATE));
    }

    @Test
    void givenTheSpanishTranslator_whenReceiveAValidAndKnownEnglishWord_thenTranslateToSpanish() {
        TranslationPack spanishTranslator = new SpanishTranslationPackAdapter();
        String wordTranslatedToSpanish = spanishTranslator.translate(WORD_TO_TRANSLATE);

        assertEquals(WORD_TRANSLATED_TO_SPANISH, wordTranslatedToSpanish);
    }

    @Test
    void givenTheSpanishTranslator_whenReceiveAnUnknownEnglishWord_thenReturnAnException() {
        TranslationPack spanishTranslator = new SpanishTranslationPackAdapter();

        assertThrows(RuntimeException.class, () -> spanishTranslator.translate(UNKNOWN_WORD_TO_TRANSLATE));
    }

    @Test
    void givenTheTranslatorService_whenReceiveANullOrEmptyWord_thenApplyBusinessLogicAndReturnAnException() {
        TranslatorService translatorService = new Translator(new PortugueseTranslationPackAdapter());

        assertThrows(IllegalArgumentException.class, () -> translatorService.translate(null));
    }

    @Test
    void givenTheTranslatorService_whenReceiveANonNullOrEmptyWord_thenApplyBusinessLogicAndTranslateTheWord() {
        TranslatorService translatorService = new Translator(new PortugueseTranslationPackAdapter());

        String translatedWord = translatorService.translate(WORD_TO_TRANSLATE);

        assertEquals(WORD_TRANSLATED_TO_PORTUGUESE, translatedWord);
    }
}
