package com.baeldung.httpclient;

import java.io.IOException;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

class HttpClientHeaderV4LiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    @Test
    void givenRequestBuildWithBuilder_whenRequestHasCustomContentType_thenCorrect() throws IOException {
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
            .setUri(SAMPLE_URL)
            .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();
        client.execute(request);
    }
}