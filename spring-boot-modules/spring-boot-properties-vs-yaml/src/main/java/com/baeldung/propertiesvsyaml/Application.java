package com.baeldung.propertiesvsyaml;

import org.springframework.beans.factory.annotation.Value;

public class Application {

    @Value("${app.name}")
    private String appName;

    public String getAppName() {
        return appName;
    }
}
