package com.baeldung.micronaut.environments.services.logging;

public interface LoggingService {

    // this method would be void, but for the purpose of our tutorial,
    // we have it returning something, so that we can easily test it.
    String log(String message);
}
