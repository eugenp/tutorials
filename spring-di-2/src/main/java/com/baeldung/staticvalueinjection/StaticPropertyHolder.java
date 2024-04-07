package com.baeldung.staticvalueinjection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticPropertyHolder {

    @Value("${name}")
    private static String STATIC_NAME_INJECTED_ON_FIELD;

    private static String STATIC_NAME;

    @Value("${name}")
    public void setStaticName(String name) {
        STATIC_NAME = name;
    }

    public static String getStaticName() {
        return STATIC_NAME;
    }

    public static String getStaticNameInjectedOnField() {
        return STATIC_NAME_INJECTED_ON_FIELD;
    }
}