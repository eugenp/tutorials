package com.baeldung.okhttp.interceptors;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class InterceptorIntegrationTest {

    @Rule
    public MockWebServer server = new MockWebServer();

    @Test
    public void givenSimpleLogginInterceptor_whenRequestSent_thenHeadersLogged() throws IOException {
        server.enqueue(new MockResponse().setBody("Hello Baeldung Readers!"));
        
        OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new SimpleLoggingInterceptor())
            .build();

        Request request = new Request.Builder()
            .url(server.url("/greeting"))
            .header("User-Agent", "A Baeldung Reader")
            .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals("Response code should be: ", 200, response.code());
            assertEquals("Body should be: ", "Hello Baeldung Readers!", response.body().string());
        }
    }
    
    @Test
    public void givenResponseInterceptor_whenRequestSent_thenCacheControlSetToNoStore() throws IOException {
        server.enqueue(new MockResponse().setBody("Hello Baeldung Readers!"));
        
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(getHttpLogger())
            .addInterceptor(new CacheControlResponeInterceptor())
            .build();

        Request request = new Request.Builder()
            .url(server.url("/greeting"))
            .header("User-Agent", "A Baeldung Reader")
            .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals("Response code should be: ", 200, response.code());
            assertEquals("Body should be: ", "Hello Baeldung Readers!", response.body().string());
            assertEquals("Response cache-control should be", "no-store", response.header("Cache-Control"));
        }
    }
    
    @Test
    public void givenErrorResponseInterceptor_whenResponseIs500_thenBodyIsJsonWithStatus() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(500).setBody("Hello Baeldung Readers!"));
        
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(getHttpLogger())
            .addInterceptor(new ErrorResponseInterceptor())
            .build();

        Request request = new Request.Builder()
            .url(server.url("/greeting"))
            .header("User-Agent", "A Baeldung Reader")
            .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals("Response code should be: ", 500, response.code());
            assertEquals("Body should be: ", "{\"status\":500,\"detail\":\"The response from the server was not OK\"}", 
                response.body().string());
        }
    }

    private HttpLoggingInterceptor getHttpLogger() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(Level.HEADERS);
        return logger;
    }

}
