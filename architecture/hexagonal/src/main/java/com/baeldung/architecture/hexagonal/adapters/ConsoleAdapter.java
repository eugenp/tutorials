package com.baeldung.architecture.hexagonal.adapters;

import com.baeldung.architecture.hexagonal.domain.Translator;
import com.baeldung.architecture.hexagonal.domain.TranslatorService;

public class ConsoleAdapter {

    public static void main(String[] args) {
        TranslatorService portugueseTranslation = new Translator(new PortugueseTranslationPackAdapter());
        String wordTranslatedToPortuguese = portugueseTranslation.translate("marathoners");

        System.out.println("marathoners translated to portuguese: " + wordTranslatedToPortuguese);

        TranslatorService spanishTranslation = new Translator(new SpanishTranslationPackAdapter());
        String wordTranslatedToSpanish = spanishTranslation.translate("marathoners");

        System.out.println("marathoners translated to spanish: " + wordTranslatedToSpanish);
    }
}
