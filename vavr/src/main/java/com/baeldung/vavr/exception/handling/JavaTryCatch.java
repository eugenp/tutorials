package com.baeldung.vavr.exception.handling;

import com.baeldung.vavr.exception.handling.client.ClientException;
import com.baeldung.vavr.exception.handling.client.HttpClient;
import com.baeldung.vavr.exception.handling.client.Response;

public class JavaTryCatch {
    private HttpClient httpClient;

    public JavaTryCatch(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Response getResponse() {
        try {
            return httpClient.call();
        } catch (ClientException e) {
            return null;
        }
    }
}
