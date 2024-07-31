package com.baeldung.resttemplate.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestTemplateClientController {
    private static final String WELCOME_URL = "https://localhost:8443/welcome";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/welcomeclient")
    public String greetMessage() {
        String response = restTemplate.getForObject(WELCOME_URL, String.class);
        return response;
    }
}
