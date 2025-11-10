package com.baeldung.retries.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateRetryService {

    private final RestTemplate restTemplate;
    private int maxRetries = 3;
    private long retryDelay = 2000;

    public RestTemplateRetryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makeRequestWithRetry(String url) {
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
                try {
                    Thread.sleep(retryDelay);
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

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }
}

