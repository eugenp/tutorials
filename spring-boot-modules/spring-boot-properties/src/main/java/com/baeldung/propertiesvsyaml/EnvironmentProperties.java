package com.baeldung.propertiesvsyaml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentProperties {

    @Autowired
    private Environment env;

    public String getSomeKey() {
        return env.getProperty("key.something");
    }
}
