package com.baeldung.httpclient;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
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
        nameValuePairs = new ArrayList<NameValuePair>();
        NameValuePair param1NameValuePair = new BasicNameValuePair("param1", "value1");
        NameValuePair param2NameValuePair = new BasicNameValuePair("param2", "value2");
        nameValuePairs.add(param1NameValuePair);
        nameValuePairs.add(param2NameValuePair);
    }

    @Test
    public void givenStringNameValuePairParams_whenGetRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet("https://postman-echo.com/get");
        URI uri = new URIBuilder(httpGet.getURI()).addParameter("param1", "value1")
            .addParameter("param2", "value2")
            .build();
        ((HttpRequestBase) httpGet).setURI(uri);
        response = client.execute(httpGet);

        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenStringNameValuePairParams_whenPostRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        URI uri = new URIBuilder(httpPost.getURI()).addParameter("param1", "value1")
            .addParameter("param2", "value2")
            .build();
        ((HttpRequestBase) httpPost).setURI(uri);
        response = client.execute(httpPost);

        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenNameValuePairParams_whenGetRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet("https://postman-echo.com/get");
        URI uri = new URIBuilder(httpGet.getURI()).addParameters(nameValuePairs)
            .build();
        ((HttpRequestBase) httpGet).setURI(uri);
        response = client.execute(httpGet);

        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenNameValuePairsParams_whenPostRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        URI uri = new URIBuilder(httpPost.getURI()).addParameters(nameValuePairs)
            .build();
        ((HttpRequestBase) httpPost).setURI(uri);
        response = client.execute(httpPost);

        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public void givenUrlEncodedEntityParams_whenPostRequest_thenResponseOk() throws URISyntaxException, ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8));
        response = client.execute(httpPost);

        assertThat(response.getStatusLine()
            .getStatusCode(), equalTo(200));
        client.close();
    }

}
