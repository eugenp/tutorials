package com.baeldung.autoservice;

import java.util.Locale;

public interface TranslateService {

    String translate(String message, Locale from, Locale to);
}
