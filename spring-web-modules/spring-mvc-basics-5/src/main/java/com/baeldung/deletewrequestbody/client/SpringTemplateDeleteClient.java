package com.baeldung.deletewrequestbody.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SpringTemplateDeleteClient {

    private final String url;
    private RestTemplate client = new RestTemplate();

    public SpringTemplateDeleteClient(String url) {
        this.url = url;
    }

    public String delete(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> body = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = client.exchange(url, HttpMethod.DELETE, body, String.class);
        return response.getBody();
    }
}
