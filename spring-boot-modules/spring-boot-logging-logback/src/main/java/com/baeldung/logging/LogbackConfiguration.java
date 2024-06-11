package com.baeldung.logging;

import org.springframework.stereotype.Component;

@Component
public class LogbackConfiguration {
    public void setLogbackConfigurationFile(String path) {
        System.setProperty("logback.configurationFile", path);
    }
}

