package com.baeldung.ambassadorpattern;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpAmbassadorClient {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public HttpAmbassadorClient(RestTemplateBuilder builder, String apiUrl,
        @Value("${http.client.connect-timeout-seconds:2000}") int connectTimeout, @Value("${http.client.read-timeout-seconds:3000}") int readTimeout) {
        this.restTemplate = builder.setConnectTimeout(Duration.ofMillis(connectTimeout))
            .setReadTimeout(Duration.ofMillis(readTimeout))
            .build();
        this.apiUrl = apiUrl;
    }

    @Cacheable(value = "httpResponses", key = "#apiUrl", unless = "#result == null")
    @Retryable(value = { HttpServerErrorException.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public String getResponse(String apiUrl, Map<String, String> params) {
        long start = System.currentTimeMillis();
        try {
            String result = restTemplate.getForObject(apiUrl, String.class, params);
            long duration = System.currentTimeMillis() - start;
            System.out.printf("HTTP call completed successfully to url=%s duration=%s%n", apiUrl, duration);
            return result;
        } catch (HttpClientErrorException e) {
            System.err.printf("HTTP Client Error error_code=%s message=%s", e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    @Recover
    public String recoverFromFailure(RuntimeException e) {
        final String defaultResponse = "default";
        System.err.printf("Too many retry attempts. Falling back to default. error=%s default=%s", e.getMessage(), defaultResponse);
        return defaultResponse;
    }
}
