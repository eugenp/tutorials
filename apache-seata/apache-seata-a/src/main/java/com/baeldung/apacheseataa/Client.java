package com.baeldung.apacheseataa;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    @Value("${service-b.url}/b/{mode}")
    private String url;

    @Autowired
    private RestClient restClient;

    public void callService(String mode) {
        LOG.info("Making request to {} with mode {}", url, mode);
        var response = restClient.post().uri(url, Map.of("mode", mode)).retrieve();
        LOG.info("Made request. Response={}", response.toBodilessEntity());
    }
}
