package com.baeldung.micronaut.environments.services;

import java.util.Set;

import io.micronaut.context.ApplicationContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class HostResolver {

    @Inject
    ApplicationContext applicationContext;

    public String getHost() {
        // avoid this messy code by using property files
        Set<String> activeEnvironments = applicationContext.getEnvironment()
            .getActiveNames();

        if (activeEnvironments.contains("dev") || activeEnvironments.contains("local")) {
            return "localhost";
        } else if (activeEnvironments.contains("production")) {
            return "my-service.us-west-2.amazonaws.com";
        } else {
            throw new RuntimeException("Unsupported environment: " + activeEnvironments);
        }
    }
}
