package com.baeldung.classtemplate;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.ClassTemplate;
import org.junit.jupiter.api.extension.ClassTemplateInvocationContext;
import org.junit.jupiter.api.extension.ClassTemplateInvocationContextProvider;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

public class GreeterClassTemplateInvocationContextProvider
        implements ClassTemplateInvocationContextProvider {

    @Override
    public boolean supportsClassTemplate(ExtensionContext context) {
        return context.getTestClass()
                      .map(c -> c.isAnnotationPresent(ClassTemplate.class))
                      .orElse(false);
    }

    @Override
    public Stream<ClassTemplateInvocationContext> provideClassTemplateInvocationContexts(
            ExtensionContext context) {

        return Stream.of(contextFor("en"), contextFor("it"));
    }

    private ClassTemplateInvocationContext contextFor(String language) {
        ParameterResolver resolver = new ParameterResolver() {
            @Override
            public boolean supportsParameter(ParameterContext pc, ExtensionContext ec) {
                return pc.getParameter().getType() == String.class;
            }

            @Override
            public Object resolveParameter(ParameterContext pc, ExtensionContext ec) {
                return language;
            }
        };

        return new ClassTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "Language-" + language;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of(resolver);
            }
        };
    }
}

