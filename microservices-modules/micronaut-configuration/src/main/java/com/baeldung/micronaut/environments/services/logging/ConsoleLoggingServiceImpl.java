package com.baeldung.micronaut.environments.services.logging;

import jakarta.inject.Singleton;

@Singleton
public class ConsoleLoggingServiceImpl implements LoggingService {

    @Override
    public String log(String message) {
        return "logging to console: [" + message + "]";
    }
}
