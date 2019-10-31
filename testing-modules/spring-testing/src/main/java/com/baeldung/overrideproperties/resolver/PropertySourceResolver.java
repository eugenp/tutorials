package com.baeldung.overrideproperties.resolver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySourceResolver {

    private final String firstProperty;
    private final String secondProperty;

    public PropertySourceResolver(@Value("${example.firstProperty}") String firstProperty, @Value("${example.secondProperty}") String secondProperty) {
        this.firstProperty = firstProperty;
        this.secondProperty = secondProperty;
    }

    public String getFirstProperty() {
        return firstProperty;
    }

    public String getSecondProperty() {
        return secondProperty;
    }
}
