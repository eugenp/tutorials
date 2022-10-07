package com.baeldung.vavr.exception.handling;


import com.baeldung.vavr.exception.handling.client.HttpClient;
import com.baeldung.vavr.exception.handling.client.Response;
import io.vavr.control.Try;

class VavrTry {
    private final HttpClient httpClient;

    VavrTry(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    Try<Response> getResponse() {
        return Try.of(httpClient::call);
    }
}
