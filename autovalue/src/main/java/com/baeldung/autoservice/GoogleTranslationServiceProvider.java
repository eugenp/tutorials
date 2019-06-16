package com.baeldung.autoservice;

import com.google.auto.service.AutoService;

import java.util.Locale;

@AutoService(TranslationService.class)
public class GoogleTranslationServiceProvider implements TranslationService {

    public String translate(String message, Locale from, Locale to) {
        return "translated by Google";
    }
}
