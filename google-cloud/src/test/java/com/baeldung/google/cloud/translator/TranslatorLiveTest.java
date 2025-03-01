package com.baeldung.google.cloud.translator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TranslatorLiveTest {

    @Test
    void whenTranslateTextIsCalledWithEnglishTextAndFrenchTargetLanguage_thenReturnTranslatedText() {
        String originalText = "Hello, world!";
        String targetLanguage = "es";
        String expectedTranslatedText = "¡Hola Mundo!";

        String translatedText = Translator.translateText(originalText, targetLanguage);

        assertEquals(expectedTranslatedText, translatedText);
    }

    @Test
    void whenTranslateTextIsCalledWithEnglishHTMLAndFrenchTargetLanguage_thenReturnTranslatedHTML() {
        String originalHtml = "<p>Hello, world!</p>";
        String targetLanguage = "es";
        String expectedTranslatedHtml = "<p>¡Hola Mundo!</p>";

        String translatedHtml = Translator.translateText(originalHtml, targetLanguage);

        assertEquals(expectedTranslatedHtml, translatedHtml);
    }

    @Test
    void whenDetectLanguageIsCalledWithSpanishText_thenReturnSpanishLanguageCode() {
        String text = "Hola, mundo!";
        String expectedLanguageCode = "es";
        String detectedLanguage = Translator.detectLanguage(text);
        assertEquals(expectedLanguageCode, detectedLanguage);
    }

    @Test
    void whenTranslateBatchIsCalledWithMultipleTexts_thenReturnTranslatedTexts() {
        List<String> originalTexts = List.of("Apple", "Banana", "Orange");
        List<String> expectedTranslatedTexts = List.of("Pomme", "Banane", "Orange");
        List<String> translatedTexts = Translator.translateBatch(originalTexts, "fr");
        assertEquals(expectedTranslatedTexts, translatedTexts);
    }
}
