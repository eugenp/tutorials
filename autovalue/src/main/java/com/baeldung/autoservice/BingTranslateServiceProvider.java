package com.baeldung.autoservice;

import com.google.auto.service.AutoService;

import java.util.Locale;

@AutoService(TranslateService.class)
public class BingTranslateServiceProvider implements TranslateService {

    public String translate(String message, Locale from, Locale to) {
        return "translated by Bing";
    }
}
