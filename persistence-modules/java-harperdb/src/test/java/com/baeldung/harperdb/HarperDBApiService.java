package com.baeldung.harperdb;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPost;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ClassicHttpRequest;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpEntity;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;

public class HarperDBApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HarperDBApiService.class);
    private String url;
    private String base64auth;

    public HarperDBApiService(String url, String user, String password) {
        this.url = url;
        this.base64auth = Base64.getEncoder()
            .encodeToString(new StringBuilder().append(user)
                .append(":")
                .append(password)
                .toString()
                .getBytes());
    }

    public void createSchema(String schema) throws IOException {
        String requestBody = "{\"operation\":\"create_schema\", \"" + "schema\":\"" + schema + "\"" + "}";
        executeHttpPostRequest(requestBody);
    }

    public void createTable(String schema, String table, String ... attributes) throws IOException {
        String createTableReq = "{\"operation\":\"create_table\","
            + "\"schema\":\"" + schema
            + "\",\"table\":\"" + table
            + "\",\"hash_attribute\":\"id\""
            + "}";
        executeHttpPostRequest(createTableReq);
        LOGGER.info("created table:" + table);
        for (String attribute : attributes) {
            String createAttrReq = "{\"operation\":\"create_attribute\",\"schema\":\""
                + schema + "\",\"table\":\""
                + table + "\",\"attribute\":\""
                + attribute + "\""
                + "}";
            executeHttpPostRequest(createAttrReq);
            LOGGER.info("created attribute:" + attribute + " in table:" + table);
        }
    }

    public void insertRecords(String schema, String table, String records) throws IOException {
        String requestBody = "{\"table\":" + "\"" + table + "\","
            + "\"schema\":" + "\"" + schema + "\"" + ","
            + "\"operation\":" + "\"" + "insert" + "\"" + ","
            + "\"records\":" + records
            + "}";
        executeHttpPostRequest(requestBody);
    }

    private void executeHttpPostRequest(String httpRequest) throws IOException {
        LOGGER.info("Post request body:" + httpRequest);

        try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
            .build()) {
            HttpPost request = new HttpPost(this.url);
            request.addHeader("Authorization", "Basic " + this.base64auth);
            request.addHeader("Content-Type", "application/json");
            request.setEntity((HttpEntity) new StringEntity(httpRequest));
            CloseableHttpResponse response = closeableHttpClient.execute((ClassicHttpRequest) request);
            LOGGER.info("REST API response:" + response.toString());
            assert (200 == response.getCode());
        }
    }
}
