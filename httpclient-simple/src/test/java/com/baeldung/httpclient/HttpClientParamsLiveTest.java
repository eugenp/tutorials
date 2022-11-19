package com.baeldung.httpclient;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import org.junit.Before;
import org.junit.Test;

public class HttpClientParamsLiveTest {

    private CloseableHttpClient client;

    private CloseableHttpResponse response;

    private List<NameValuePair> nameValuePairs;

    @Before
    public void setUp() {
        client = HttpClientBuilder.create()
            .build();
        nameValuePairs = new ArrayList<>();
        NameValuePair param1NameValuePair = new BasicNameValuePair("param1", "value1");
        NameValuePair param2NameValuePair = new BasicNameValuePair("param2", "value2");
        nameValuePairs.add(param1NameValuePair);
        nameValuePairs.add(param2NameValuePair);
    }

    @Test
    public void givenStringNameValuePairParams_whenGetRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet("https://postman-echo.com/get");
        URI uri = new URIBuilder(httpGet.getUri()).addParameter("param1", "value1")
            .addParameter("param2", "value2")
            .build();
        httpGet.setUri(uri);

        response = client.execute(httpGet);
        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenStringNameValuePairParams_whenPostRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        URI uri = new URIBuilder(httpPost.getUri()).addParameter("param1", "value1")
            .addParameter("param2", "value2")
            .build();
        httpPost.setUri(uri);
        response = client.execute(httpPost);

        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenNameValuePairParams_whenGetRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet("https://postman-echo.com/get");
        URI uri = new URIBuilder(httpGet.getUri()).addParameters(nameValuePairs)
            .build();
        httpGet.setUri(uri);
        response = client.execute(httpGet);

        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenNameValuePairsParams_whenPostRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        URI uri = new URIBuilder(httpPost.getUri()).addParameters(nameValuePairs)
            .build();
        httpPost.setUri(uri);
        response = client.execute(httpPost);

        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenUrlEncodedEntityParams_whenPostRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
        response = client.execute(httpPost);

        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

}
