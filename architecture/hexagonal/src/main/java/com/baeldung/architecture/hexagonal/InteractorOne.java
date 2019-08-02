package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.domain.Translator;
import com.baeldung.architecture.hexagonal.domain.TranslatorService;
import com.baeldung.architecture.hexagonal.interactorTwo.PortugueseTranslator;
import com.baeldung.architecture.hexagonal.interactorTwo.SpanishTranslator;

public class InteractorOne {
    public static void main(String[] args) {
        TranslatorService portugueseTranslation = new Translator(new PortugueseTranslator());
        String wordTranslatedToPortuguese = portugueseTranslation.translate("marathoners");

        System.out.println("marathoners translated to portuguese: " + wordTranslatedToPortuguese);

        TranslatorService spanishTranslation = new Translator(new SpanishTranslator());
        String wordTranslatedToSpanish = spanishTranslation.translate("marathoners");

        System.out.println("marathoners translated to spanish: " + wordTranslatedToSpanish);
    }
}
