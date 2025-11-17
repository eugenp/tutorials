package com.baeldung.retries.controller;

import com.baeldung.retries.service.ExponentialBackoffRetryService;
import com.baeldung.retries.service.RestTemplateRetryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RetryController {

    private final RestTemplateRetryService retryService;
    private final ExponentialBackoffRetryService exponentialService;

    public RetryController(RestTemplateRetryService retryService,
                           ExponentialBackoffRetryService exponentialService) {
        this.retryService = retryService;
        this.exponentialService = exponentialService;
    }

    @GetMapping("/fetch-with-retry")
    public ResponseEntity<String> fetchWithRetry(@RequestParam String url) {
        try {
            String result = retryService.makeRequestWithRetry(url);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(503)
                    .body("Service unavailable after retries: " + e.getMessage());
        }
    }

    @GetMapping("/fetch-with-exponential-backoff")
    public ResponseEntity<String> fetchWithExponentialBackoff(
            @RequestParam String url) {
        try {
            String result = exponentialService
                    .makeRequestWithExponentialBackoff(url);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(503)
                    .body("Service unavailable after retries: " + e.getMessage());
        }
    }
}

