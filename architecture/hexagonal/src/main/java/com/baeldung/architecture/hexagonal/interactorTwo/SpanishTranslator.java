package com.baeldung.architecture.hexagonal.interactorTwo;


import com.baeldung.architecture.hexagonal.domain.TranslatorPack;

public class SpanishTranslator implements TranslatorPack {

    public String translate(String englishWordToTranslate) {
        switch (englishWordToTranslate) {
            case "marathoners":
                return "corredores de maratón";
            default:
                throw new RuntimeException("Word not translated yet");
        }
    }
}
