package com.baeldung.architecture.hexagonal.adapters;

import com.baeldung.architecture.hexagonal.domain.TranslationPack;

public class SpanishTranslationPackAdapter implements TranslationPack {

    public String translate(String englishWordToTranslate) {
        switch (englishWordToTranslate) {
            case "marathoners":
                return "corredores de maratón";
            default:
                throw new RuntimeException("Word not translated yet");
        }
    }
}
