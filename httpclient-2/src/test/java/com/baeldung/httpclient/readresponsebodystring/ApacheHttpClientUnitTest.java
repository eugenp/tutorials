package com.baeldung.httpclient.readresponsebodystring;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class ApacheHttpClientUnitTest {
    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    public void whenUseApacheHttpClient_thenCorrect() throws IOException {
        HttpGet request = new HttpGet(DUMMY_URL);

        try (CloseableHttpClient client = HttpClients.createDefault(); CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            System.out.println("Response -> " + result);
        }
    }
}
