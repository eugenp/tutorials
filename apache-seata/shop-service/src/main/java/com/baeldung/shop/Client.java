package com.baeldung.shop;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private String url;

    private RestClient restClient;

    public Client(String url, RestClient restClient) {
        this.url = url;
        this.restClient = restClient;
    }

    public void callService(String mode) {
        LOG.info("Making request to {} with mode {}", url, mode);
        var response = restClient.post().uri(url, Map.of("mode", mode)).retrieve();
        LOG.info("Made request. Response={}", response.toBodilessEntity());
    }
}
