package com.baeldung.java9.httpclient;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

public class AsyncHttpClientTest {

    private URI httpURI;

    @Before
    public void init() throws URISyntaxException {
        httpURI = new URI("http://www.baeldung.com/");
    }

    @Test
    public void asynchronousGet() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        HttpRequest request = HttpRequest.create(httpURI).GET();
        long before = System.currentTimeMillis();
        CompletableFuture<HttpResponse> futureResponse = request.responseAsync();
        futureResponse.thenAccept(response -> {
            String responseBody = response.body(HttpResponse.asString());
        });
        HttpResponse resp = futureResponse.get();
        HttpHeaders hs = resp.headers();
        assertTrue("There should be more then 1 header.", hs.map().size() > 1);
    }

}
