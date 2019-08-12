package com.baeldung.architecture.hexagonal.domain;

public class Translator implements TranslatorService {

    private final TranslationPack translationPack;

    public Translator(TranslationPack translationPack) {
        this.translationPack = translationPack;
    }

    @Override
    public String translate(String englishWordToTranslate) {
        // Business logic goes here
        if (englishWordToTranslate == null || englishWordToTranslate.trim().isEmpty()) {
            throw new IllegalArgumentException("Please inform the word to translate");
        }

        return translationPack.translate(englishWordToTranslate);
    }
}
