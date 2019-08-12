package com.baeldung.architecture.hexagonal.adapters;


import com.baeldung.architecture.hexagonal.domain.TranslationPack;

public class PortugueseTranslationPackAdapter implements TranslationPack {

    public String translate(String englishWordToTranslate) {
        switch (englishWordToTranslate) {
            case "marathoners":
                return "maratonistas";
            default:
                throw new RuntimeException("Word not translated yet");
        }
    }
}
