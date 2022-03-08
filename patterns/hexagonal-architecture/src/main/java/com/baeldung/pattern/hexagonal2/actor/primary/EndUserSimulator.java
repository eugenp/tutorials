package com.baeldung.pattern.hexagonal2.actor.primary;

import java.util.UUID;

import org.bson.json.JsonObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
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

    private EndUserSimulator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private void createUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
            new HttpEntity<>(newUser, headers);
        String response = this.restTemplate.postForObject(ENDPOINT_URL, request, String.class);
        System.out.println(response);
    }

    private void requestUsers() {
        String response = this.restTemplate.getForObject(ENDPOINT_URL, String.class);
        System.out.println(response);
    }

    private void deleteUser() {
        this.restTemplate.delete(ENDPOINT_URL + "/" + this.newUserId);
        System.out.println("User successfully deleted.");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerEndUserSimulator() throws InterruptedException {
        Thread.sleep(4000);
        createUser();
        requestUsers();
        //deleteUser();
    }

}
