package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.handler.CustomHttpClientResponseHandler;

class HttpClientParamsLiveTest {


    private List<NameValuePair> nameValuePairs;

    @BeforeEach
    public void setUp() {
        nameValuePairs = new ArrayList<>();
        NameValuePair param1NameValuePair = new BasicNameValuePair("param1", "value1");
        NameValuePair param2NameValuePair = new BasicNameValuePair("param2", "value2");
        nameValuePairs.add(param1NameValuePair);
        nameValuePairs.add(param2NameValuePair);
    }

    @Test
    void givenStringNameValuePairParams_whenGetRequest_thenResponseOk() throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet("https://postman-echo.com/get");
        URI uri = new URIBuilder(httpGet.getUri()).addParameter("param1", "value1")
            .addParameter("param2", "value2")
            .build();
        httpGet.setUri(uri);

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpGet, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void givenStringNameValuePairParams_whenPostRequest_thenResponseOk() throws URISyntaxException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        URI uri = new URIBuilder(httpPost.getUri()).addParameter("param1", "value1")
            .addParameter("param2", "value2")
            .build();
        httpPost.setUri(uri);

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void givenNameValuePairParams_whenGetRequest_thenResponseOk() throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet("https://postman-echo.com/get");
        URI uri = new URIBuilder(httpGet.getUri()).addParameters(nameValuePairs)
            .build();
        httpGet.setUri(uri);

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpGet, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void givenNameValuePairsParams_whenPostRequest_thenResponseOk() throws URISyntaxException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        URI uri = new URIBuilder(httpPost.getUri()).addParameters(nameValuePairs)
            .build();
        httpPost.setUri(uri);

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void givenUrlEncodedEntityParams_whenPostRequest_thenResponseOk() throws IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));

        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(httpPost, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

}
