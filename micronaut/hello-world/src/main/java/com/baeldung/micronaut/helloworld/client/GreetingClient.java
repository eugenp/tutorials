package com.baeldung.micronaut.helloworld.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.Client;
import io.micronaut.http.client.RxHttpClient;

import javax.inject.Inject;

@Client("/greet")
public interface GreetingClient {

    @Get("/{name}")
    String greet(String name);
}
