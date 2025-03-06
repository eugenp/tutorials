package com.baeldung.google.cloud.translator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.v3.GlossaryName;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextGlossaryConfig;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.TranslationServiceClient;

public class Translator {

    private static final Logger logger = LoggerFactory.getLogger(Translator.class);

    private static Translate translate;

    static {
        initializeTranslateClient();
    }

    public static void initializeTranslateClient() {
        if (translate == null) {
            try {
                GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new FileInputStream("src/main/resources/translator_permission.json")
                );
                translate = TranslateOptions.newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();
                logger.info("Google Translate client initialized.");
            } catch (Exception e) {
                logger.error("Failed to initialize Google Translate client.", e);
            }
        }
    }

    public static void listSupportedLanguages() {
        try {
            List<Language> languages = translate.listSupportedLanguages();
            for (Language language : languages) {
                logger.info(String.format("Name: %s, Code: %s", language.getName(), language.getCode()));
            }
        } catch (Exception e) {
            // handle exception
        }
    }

    public static void listSupportedLanguagesWithSpecificLanguage() {
        try {
            List<Language> languages = translate.listSupportedLanguages(Translate.LanguageListOption.targetLanguage("es"));
            for (Language language : languages) {
                logger.info(String.format("Name: %s, Code: %s", language.getName(), language.getCode()));
            }
        } catch (Exception e) {
            // handle exception
        }
    }

    public static String translateText(String text, String targetLanguage) {
        String s = "";
        try {
            Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
            s = translation.getTranslatedText();
        } catch (Exception e) {
            e.printStackTrace();
            // handle exception
        }
        return s;
    }

    public static String detectLanguage(String text) {
        return translate.detect(text)
            .getLanguage();
    }

    public static List<String> translateBatch(List<String> texts, String targetLanguage) {
        List<String> translationList = null;
        try {
            List<Translation> translations = translate.translate(texts, Translate.TranslateOption.targetLanguage(targetLanguage));
            translationList = translations.stream()
                .map(Translation::getTranslatedText)
                .collect(Collectors.toList());
        } catch (Exception e) {
            // handle exception
        }
        return translationList;
    }

    public static String translateWithGlossary(String projectId, String location, String text, String targetLanguage, String glossaryId) {
        String translatedText = "";

        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            LocationName parent = LocationName.of(projectId, location);
            GlossaryName glossaryName = GlossaryName.of(projectId, location, glossaryId);

            TranslateTextRequest request = TranslateTextRequest.newBuilder()
                .setParent(parent.toString())
                .setTargetLanguageCode(targetLanguage)
                .addContents(text)
                .setGlossaryConfig(TranslateTextGlossaryConfig.newBuilder()
                    .setGlossary(glossaryName.toString()).build()) // Attach glossary
                .build();

            TranslateTextResponse response = client.translateText(request);
            translatedText = response.getTranslations(0).getTranslatedText();
        } catch (IOException e) {
            // handle exception
        }
        return translatedText;
    }

}
