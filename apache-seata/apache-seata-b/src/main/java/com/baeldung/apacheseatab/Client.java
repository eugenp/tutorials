package com.baeldung.apacheseatab;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    @Value("${service-c.url}/c/{mode}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public void callService(String mode) {
        LOG.info("Making request to {} with mode {}", url, mode);
        var response = restTemplate.postForEntity(url, null, Void.class, Map.of("mode", mode));
        LOG.info("Made request. Response={}", response);
    }
}
