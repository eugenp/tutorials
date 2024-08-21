package com.baeldung.micronaut.environments.services.logging;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Singleton
@Requires(env = { "production", "canary-production" })
@Replaces(LoggingService.class)
public class FileLoggingServiceImpl implements LoggingService {

    @Override
    public String log(String message) {
        return "logging to some file: [" + message + "]";
    }
}
