package com.baeldung.nlp;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenNLPLanguageDetector {

    @Test
    public void givenTextInEnglish_whenDetectLanguage_thenReturnsEnglishLanguageCode() {

        String text = "the dream my father told me";
        LanguageDetectorModel model;

        try (InputStream modelIn = new FileInputStream("langdetect-183.bin")) {
            model = new LanguageDetectorModel(modelIn);
        } catch (IOException e) {
            return;
        }

        LanguageDetectorME detector = new LanguageDetectorME(model);
        Language language = detector.predictLanguage(text);

        assertEquals("eng", language.getLang());
    }
}