package com.baeldung.architecture.hexagonal.domain;

public class Translator implements TranslatorService {

    private final TranslatorPack translatorPack;

    public Translator(TranslatorPack translatorPack) {
        this.translatorPack = translatorPack;
    }

    @Override
    public String translate(String englishWordToTranslate) {
        // Business logic goes here
        if (englishWordToTranslate == null || englishWordToTranslate.trim().isEmpty()) {
            throw new IllegalArgumentException("Please inform the word to translate");
        }

        return translatorPack.translate(englishWordToTranslate);
    }
}
