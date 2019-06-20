package com.baeldung.autoservice;

import java.util.Locale;

public interface TranslationService {
    String translate(String message, Locale from, Locale to);
}