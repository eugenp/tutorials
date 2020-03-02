package com.baeldung.hexagonal.architecture.application.adapters.output;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.architecture.core.ports.HttpPort;

@Component
public class HttpClientAdapter implements HttpPort {

    HttpClient client;

    public HttpClientAdapter() {
        client = HttpClientBuilder.create()
            .build();
    }

    public Object doGet(String url) throws IOException, InterruptedException {
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request);

        String result = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }

        return result;
    }

}
