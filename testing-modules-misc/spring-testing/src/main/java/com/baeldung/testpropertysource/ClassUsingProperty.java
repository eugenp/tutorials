package com.baeldung.testpropertysource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClassUsingProperty {
    
    @Value("${baeldung.testpropertysource.one}")
    private String propertyOne;
    
    public String retrievePropertyOne() {
        return propertyOne;
    }
}
