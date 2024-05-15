package com.baeldung.jasypt.simple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceForJasyptSimple {

    @Value("${encryptedv2.property}")
    private String property;

    public String getProperty() {
        return property;
    }
}
