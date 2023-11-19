package com.baeldung.handler;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

public class CustomHttpClientResponseHandler implements HttpClientResponseHandler<ClassicHttpResponse> {
    @Override
    public ClassicHttpResponse handleResponse(ClassicHttpResponse response) {
        return response;
    }
}