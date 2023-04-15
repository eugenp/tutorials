package com.baeldung.autoservice;

import com.google.auto.service.AutoService;

import java.util.Locale;

@AutoService(TranslationService.class)
public class BingTranslationServiceProvider implements TranslationService {
    @Override
    public String translate(String message, Locale from, Locale to) {
        // implementation details
        return message + " (translated by Bing)"; 
    }
}
