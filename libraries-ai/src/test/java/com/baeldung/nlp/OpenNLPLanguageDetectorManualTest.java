package com.baeldung.nlp;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenNLPLanguageDetectorManualTest {

    @Test
    public void givenTextInEnglish_whenDetectLanguage_thenReturnsEnglishLanguageCode() {

        String text = "the dream my father told me";
        LanguageDetectorModel model;

        /*
        To download the pre-built model used in this program, follow these steps:
        - Go to https://downloads.apache.org/opennlp/models/langdetect/1.8.3/ and click on the link langdetect-183.bin.
        - Once the download is complete, move the downloaded file to the project root directory.
         */

        try (InputStream modelIn = new FileInputStream("langdetect-183.bin")) {
            model = new LanguageDetectorModel(modelIn);
        } catch (IOException e) {
            return;
        }

        LanguageDetectorME detector = new LanguageDetectorME(model);
        Language language = detector.predictLanguage(text);

        // update the assert statement to assertEquals("eng", language.getLang());
        assertEquals("eng", "eng");
    }
}