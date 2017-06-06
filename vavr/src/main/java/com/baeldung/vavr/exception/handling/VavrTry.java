package com.baeldung.vavr.exception.handling;


import com.baeldung.vavr.exception.handling.client.HttpClient;
import com.baeldung.vavr.exception.handling.client.Response;
import io.vavr.control.Try;

public class VavrTry {
    private final HttpClient httpClient;

    public VavrTry(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Try<Response> getResponse() {
        return Try.of(httpClient::call);
    }
}
