package com.baeldung.beaninjection;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlainTextMailFormatter implements RegistrationMailFormatter {
    // normally, this will be loaded from external file
    private static final String content = "Welcome to Wonderful Inc.\n\nYour login: {{username}}\nYour password: {{password}}";

    public String format(Map<String, String> context) {
        String replacedContent = content;
        for (Map.Entry<String, String> variables : context.entrySet()) {
            replacedContent = replacedContent.replace("{{" + variables.getKey() + "}}", variables.getValue());
        }
        return replacedContent;
    }
}
