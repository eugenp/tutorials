package com.baeldung.pattern.hexagonal2.actor.primary;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EndUserSimulator {

    private final RestTemplate restTemplate;
    private final String ENDPOINT_URL = "http://localhost:8080/api/v1/user/";
    private final UUID newUserId = UUID.fromString("f52c9e91-4132-4318-9e7a-79517421a510");
    private final String newUser = String.format("{\"id\": \"%s\", \"userName\":\"my.new@user.com\"}", newUserId);
    private static Logger logger = LoggerFactory.getLogger(EndUserSimulator.class.getSimpleName());

    private EndUserSimulator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    void createUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(newUser, headers);
        String response = this.restTemplate.postForObject(ENDPOINT_URL, request, String.class);
        logger.info(String.format("Successfully created user: %s", response));
    }

    void requestUsers() {
        String response = this.restTemplate.getForObject(ENDPOINT_URL, String.class);
        logger.info(String.format("List all users: %s", response));
    }

    void deleteUser() {
        this.restTemplate.delete(ENDPOINT_URL + "/" + this.newUserId);
        logger.info("User successfully deleted.");
    }
}
