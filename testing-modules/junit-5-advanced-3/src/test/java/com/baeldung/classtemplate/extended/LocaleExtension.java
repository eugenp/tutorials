package com.baeldung.classtemplate.extended;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Locale;

class LocaleExtension implements BeforeEachCallback, AfterEachCallback {

    private final Locale locale;
    private Locale previous;

    public LocaleExtension(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        previous = Locale.getDefault();
        Locale.setDefault(locale);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Locale.setDefault(previous);
    }
}

