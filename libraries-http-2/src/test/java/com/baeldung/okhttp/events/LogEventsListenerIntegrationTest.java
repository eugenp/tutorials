package com.baeldung.okhttp.events;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.junit.Rule;
import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class LogEventsListenerIntegrationTest {
    
    @Rule
    public MockWebServer server = new MockWebServer();

    @Test
    public void givenSimpleEventLogger_whenRequestSent_thenCallsLogged() throws IOException {
        server.enqueue(new MockResponse().setBody("Hello Baeldung Readers!"));
        
        OkHttpClient client = new OkHttpClient.Builder()
            .eventListener(new SimpleLogEventsListener())
            .build();

        Request request = new Request.Builder()
            .url(server.url("/"))
            .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals("Response code should be: ", 200, response.code());
            assertEquals("Body should be: ", "Hello Baeldung Readers!", response.body().string());
        }
    }
    
    @Test (expected = SocketTimeoutException.class)
    public void givenConnectionError_whenRequestSent_thenFailedCallsLogged() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
            .eventListener(new EventTimer())
            .build();

        Request request = new Request.Builder()
            .url(server.url("/"))
            .build();

        client.newCall(request).execute();
    }

}
