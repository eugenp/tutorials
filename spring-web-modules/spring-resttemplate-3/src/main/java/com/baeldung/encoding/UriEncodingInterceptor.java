package com.baeldung.encoding;


import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

public class UriEncodingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequest encodedRequest = new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                URI uri = super.getURI();
                String escapedQuery = uri.getRawQuery().replace("+", "%2B");
                return UriComponentsBuilder.fromUri(uri)
                  .replaceQuery(escapedQuery)
                  .build(true).toUri();
            }
        };
        return execution.execute(encodedRequest, body);
    }
}