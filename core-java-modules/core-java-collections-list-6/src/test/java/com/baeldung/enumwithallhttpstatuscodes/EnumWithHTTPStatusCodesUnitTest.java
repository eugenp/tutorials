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

public class EnumWithHTTPStatusCodesUnitTest {
    @Test
    public void givenStatusCode_whenUsingBasicApproach_thenGetCorrectCode() {
        assertEquals(100, HTTPStatus.CONTINUE.getCode());
        assertEquals(200, HTTPStatus.OK.getCode());
        assertEquals(300, HTTPStatus.MULTIPLE_CHOICES.getCode());
        assertEquals(400, HTTPStatus.BAD_REQUEST.getCode());
        assertEquals(500, HTTPStatus.INTERNAL_SERVER_ERROR.getCode());
    }

    @Test
    public void givenStatusCode_whenUsingBasicApproach_thenGetCorrectDescription() {
        assertEquals("Continue", HTTPStatus.CONTINUE.getDescription());
        assertEquals("OK", HTTPStatus.OK.getDescription());
        assertEquals("Multiple Choices", HTTPStatus.MULTIPLE_CHOICES.getDescription());
        assertEquals("Bad Request", HTTPStatus.BAD_REQUEST.getDescription());
        assertEquals("Internal Server Error", HTTPStatus.INTERNAL_SERVER_ERROR.getDescription());
    }

    @Test
    public void givenStatusCode_whenGetStatusDescription_thenCorrectDescription() {
        assertEquals("Continue", HTTPStatusUtil.getStatusDescription(100));
        assertEquals("OK", HTTPStatusUtil.getStatusDescription(200));
        assertEquals("Multiple Choices", HTTPStatusUtil.getStatusDescription(300));
        assertEquals("Bad Request", HTTPStatusUtil.getStatusDescription(400));
        assertEquals("Internal Server Error", HTTPStatusUtil.getStatusDescription(500));
    }

    @Test
    public void givenHttpRequest_whenUsingApacheHttpComponents_thenCorrectStatusDescription() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://example.com");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String statusDescription = HTTPStatusUtil.getStatusDescription(statusCode);
            assertEquals("OK", statusDescription);
        }
    }

    @Test
    public void givenHttpRequest_whenUsingSpringRestTemplate_thenCorrectStatusDescription() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://example.com", String.class);
        int statusCode = response.getStatusCode().value();
        String statusDescription = HTTPStatusUtil.getStatusDescription(statusCode);
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
            String statusDescription = HTTPStatusUtil.getStatusDescription(statusCode);
            assertEquals("OK", statusDescription);
        }
    }
}
