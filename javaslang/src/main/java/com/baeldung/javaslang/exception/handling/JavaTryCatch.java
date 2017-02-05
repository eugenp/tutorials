package com.baeldung.javaslang.exception.handling;

import com.baeldung.javaslang.exception.handling.client.ClientException;
import com.baeldung.javaslang.exception.handling.client.HttpClient;
import com.baeldung.javaslang.exception.handling.client.Response;

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
