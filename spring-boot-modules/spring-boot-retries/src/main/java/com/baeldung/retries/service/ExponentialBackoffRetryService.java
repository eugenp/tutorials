package com.baeldung.retries.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExponentialBackoffRetryService {

    private final RestTemplate restTemplate;
    private int maxRetries = 5;
    private long initialDelay = 1000;

    public ExponentialBackoffRetryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makeRequestWithExponentialBackoff(String url) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                return restTemplate.getForObject(url, String.class);
            } catch (ResourceAccessException e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new RuntimeException(
                            "Failed after " + maxRetries + " attempts", e);
                }
                long delay = initialDelay * (long) Math.pow(2, attempt - 1);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }
        throw new RuntimeException("Unexpected error in retry logic");
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }
}

