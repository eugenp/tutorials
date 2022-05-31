package com.baeldung.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AlbumService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumService.class);

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    private RestTemplate restTemplate = new RestTemplate();

    public String getAlbumList() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        String url = "https://jsonplaceholder.typicode.com/albums";

        return circuitBreaker.run(() -> restTemplate.getForObject(url, String.class), throwable -> getDefaultAlbumList());
    }

    private String getDefaultAlbumList() {
        try {
            return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("fallback-album-list.json").toURI())));
        } catch (Exception e) {
            LOGGER.error("error occurred while reading the file", e);
        }
        return null;
    }

}
