package com.baeldung.testpropertysource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClassUsingProperty {
    
    @Value("${baeldung.testpropertysource.one}")
    private String propertyOne;

    @Value("${baeldung.testpropertysource.two}")
    private String propertyTwo;

    public String retrievePropertyOne() {
        return propertyOne;
    }

    public String retrievePropertyTwo() {
        return propertyTwo;
    }
}
