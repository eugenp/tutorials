package com.baeldung.retries.service;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RetryTemplateService {

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    public RetryTemplateService(RestTemplate restTemplate, RetryTemplate retryTemplate) {
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
    }

    public String fetchDataWithRetryTemplate(String url) {
        return retryTemplate.execute(context -> {
            return restTemplate.getForObject(url, String.class);
        }, context -> {
            return "Fallback response";
        });
    }
}

