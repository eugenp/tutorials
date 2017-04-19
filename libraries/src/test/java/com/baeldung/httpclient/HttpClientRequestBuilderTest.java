package com.baeldung.httpclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.http.HttpResponseWrapper;

public class HttpClientRequestBuilderTest {
    private HttpClientRequestBuilder requestBuilder;

    @Before
    public void setup() {
        requestBuilder = new HttpClientRequestBuilder();
    }

    @Test
    public void whenGetRequest_thenOk() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        HttpResponseWrapper response = requestBuilder.sendGetRequest("http://www.example.com",parameters);
        assertEquals("status code incorrect", response.getStatus(), 200);
        assertTrue("content incorrect", response.getContent()
            .contains("Example Domain"));
    }

    @Test
    public void whenPostRequestWithParameters_thenOk() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        HttpResponseWrapper response = requestBuilder.sendPostRequestWithParameters("http://www.example.com", parameters);
        assertEquals("status code incorrect", response.getStatus(), 200);
    }
    
    @Test
    public void whenPostRequestWithJson_thenOk() {
        String json = "{\"id\":\"1\"}";
        HttpResponseWrapper response = requestBuilder.sendPostRequestWithJson("http://www.example.com",json);
        assertEquals("status code incorrect", response.getStatus(), 200);
    }
}
