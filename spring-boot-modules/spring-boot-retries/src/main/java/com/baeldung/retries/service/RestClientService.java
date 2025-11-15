package com.baeldung.retries.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
public class RestClientService {

    private final RestTemplate restTemplate;

    public RestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(
            retryFor = {ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000))
    public String fetchData(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    @Recover
    public String recover(ResourceAccessException e, String url) {
        return "Fallback response after all retries failed for: " + url;
    }
}

