package com.baeldung.enumwithallhttpstatuscodes;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EnumWithHttpStatusCodesLiveTest {
    @Test
    public void givenHttpRequest_whenUsingApacheHttpComponents_thenCorrectStatusDescription() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://example.com");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String reasonPhrase = response.getStatusLine().getReasonPhrase();
            assertEquals("OK", reasonPhrase);
        }
    }

    @Test
    public void givenHttpRequest_whenUsingSpringRestTemplate_thenCorrectStatusDescription() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://example.com", String.class);
        int statusCode = response.getStatusCode().value();
        String statusDescription = HttpStatus.getStatusFromCode(statusCode).getDescription();
        assertEquals("OK", statusDescription);
    }

    @Test
    public void givenHttpRequest_whenUsingOkHttp_thenCorrectStatusDescription() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("http://example.com")
            .build();
        try (Response response = client.newCall(request).execute()) {
            int statusCode = response.code();
            String statusDescription = HttpStatus.getStatusFromCode(statusCode).getDescription();
            assertEquals("OK", statusDescription);
        }
    }
}