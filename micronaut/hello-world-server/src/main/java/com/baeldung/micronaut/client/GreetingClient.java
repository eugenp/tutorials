package com.baeldung.micronaut.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.Client;

@Client("/greet")
public interface GreetingClient {
    @Get("/{name}")
    String greet(String name);
}
