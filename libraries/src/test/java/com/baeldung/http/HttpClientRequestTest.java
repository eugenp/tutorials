package com.baeldung.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.http.HttpResponseWrapper;

public class HttpClientRequestTest {
    private HttpClient client;

    @Before
    public void setup() {
        client = HttpClientBuilder.create().build();
    }

    @Test
    public void whenGetRequest_thenOk() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        String url = "http://example.com?" + ParameterStringBuilder.getParamsString(parameters);

        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
        responseWrapper.setStatus(response.getStatusLine().getStatusCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line, content = "";
        while ((line = in.readLine()) != null) {
            content += line;
        }
        responseWrapper.setContent(content);

        assertEquals("status code incorrect", responseWrapper.getStatus(), 200);
        assertTrue("content incorrect", responseWrapper.getContent().contains("Example Domain"));
    }

    @Test
    public void whenPostRequestWithParameters_thenOk() throws IOException {
        HttpPost request = new HttpPost("http://example.com");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        parameters.put("param2", "val");

        List<NameValuePair> nameValuePairs = parameters.entrySet().stream().map(e -> new BasicNameValuePair(e.getKey(), e.getValue())).collect(Collectors.toList());
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(request);

        HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
        responseWrapper.setStatus(response.getStatusLine().getStatusCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line, content = "";
        while ((line = in.readLine()) != null) {
            content += line;
        }
        responseWrapper.setContent(content);

        assertEquals("status code incorrect", responseWrapper.getStatus(), 200);
    }

    @Test
    public void whenPostRequestWithJson_thenOk() throws IOException {
        HttpPost request = new HttpPost("http://example.com");
        request.addHeader("Content-Type", "application/json");
        String json = "{\"id\":\"1\"}";
        request.setEntity(new StringEntity(json));

        HttpResponse response = client.execute(request);

        HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
        responseWrapper.setStatus(response.getStatusLine().getStatusCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line, content = "";
        while ((line = in.readLine()) != null) {
            content += line;
        }
        responseWrapper.setContent(content);

        assertEquals("status code incorrect", responseWrapper.getStatus(), 200);
    }
}
