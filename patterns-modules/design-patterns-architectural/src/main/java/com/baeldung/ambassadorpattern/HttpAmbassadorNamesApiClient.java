package com.baeldung.ambassadorpattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpAmbassadorNamesApiClient {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(HttpAmbassadorNamesApiClient.class);
    public final String apiUrl;

    public HttpAmbassadorNamesApiClient(RestTemplate restTemplate, @Value("${names-api-url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Cacheable(value = "httpResponses", key = "#root.target.apiUrl", unless = "#result == null")
    @Retryable(value = { HttpServerErrorException.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public String getResponse() {
        try {
            String result = restTemplate.getForObject(apiUrl, String.class);
            logger.info("HTTP call completed successfully to url={}", apiUrl);
            return result;
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Client Error error_code={} message={}", e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    @Recover
    public String recover(Exception e) {
        final String defaultResponse = "default";
        logger.error("Too many retry attempts. Falling back to default. error={} default={}", e.getMessage(), defaultResponse);
        return defaultResponse;
    }
}
