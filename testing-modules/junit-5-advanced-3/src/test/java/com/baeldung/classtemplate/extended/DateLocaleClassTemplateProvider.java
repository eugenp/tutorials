package com.baeldung.classtemplate.extended;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ClassTemplateInvocationContext;
import org.junit.jupiter.api.extension.ClassTemplateInvocationContextProvider;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

class DateLocaleClassTemplateProvider implements ClassTemplateInvocationContextProvider {

    @Override
    public boolean supportsClassTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<ClassTemplateInvocationContext> provideClassTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(invocationContext(Locale.US), invocationContext(Locale.GERMANY), invocationContext(Locale.ITALY), invocationContext(Locale.JAPAN));
    }

    private ClassTemplateInvocationContext invocationContext(Locale locale) {
        return new ClassTemplateInvocationContext() {

            @Override
            public String getDisplayName(int invocationIndex) {
                return "Locale: " + locale.getDisplayName();
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(new LocaleExtension(locale));
            }
        };
    }
}

