package com.baeldung.dynamicendpoints.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfigBean {

    private final Environment environment;

    public EnvironmentConfigBean(@Autowired Environment environment) {
        this.environment = environment;
    }

    public String getEndpointRegex() {
        return environment.getProperty("endpoint.regex");
    }

    public boolean isFooEndpointEnabled() {
        return Boolean.parseBoolean(environment.getProperty("endpoint.foo"));
    }

    public Environment getEnvironment() {
        return environment;
    }
}
