package com.baeldung.deletewrequestbody.client;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.baeldung.deletewrequestbody.client.helper.HttpDeleteBody;

public class ApacheDeleteClient {

    private final String url;

    public ApacheDeleteClient(String url) {
        this.url = url;
    }

    public String delete(String json) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            StringEntity body = new StringEntity(json, ContentType.APPLICATION_JSON);

            HttpDeleteBody delete = new HttpDeleteBody(url);
            delete.setEntity(body);

            CloseableHttpResponse response = client.execute(delete);
            return EntityUtils.toString(response.getEntity());
        }
    }
}
