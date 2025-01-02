package com.baeldung.deletewrequestbody.client;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class Apache5DeleteClient {

    private final String url;

    public Apache5DeleteClient(String url) {
        this.url = url;
    }

    public String delete(String json) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            StringEntity body = new StringEntity(json, ContentType.APPLICATION_JSON);

            HttpDelete delete = new HttpDelete(url);
            delete.setEntity(body);

            HttpClientResponseHandler<String> handler = response -> {
                try (HttpEntity entity = response.getEntity()) {
                    return EntityUtils.toString(entity);
                }
            };

            return client.execute(delete, handler);
        }
    }
}
