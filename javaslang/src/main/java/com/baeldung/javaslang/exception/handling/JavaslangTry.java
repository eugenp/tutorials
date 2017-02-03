package com.baeldung.javaslang.exception.handling;


import com.baeldung.javaslang.exception.handling.client.HttpClient;
import com.baeldung.javaslang.exception.handling.client.Response;
import javaslang.control.Try;

public class JavaslangTry {
    private final HttpClient httpClient;

    public JavaslangTry(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Try<Response> getResponse() {
        return Try.of(httpClient::call);
    }
}
