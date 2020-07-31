package com.baeldung.propertiesvsyaml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEnvironment {
    private final Environment environment;

    @Autowired
    public ApplicationEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getApplicationName() {
        return environment.getProperty("app.name");
    }
}
