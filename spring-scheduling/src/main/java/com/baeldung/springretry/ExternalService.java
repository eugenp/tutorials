package com.baeldung.springretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class ExternalService {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalService.class);

    private int attempt = 0;

    @Retryable(maxRetries = 2, delay = 500)
    public void callExternalApi() {

        attempt++;
        LOG.info("Attempt {} - Calling external API...", attempt);

        throw new RuntimeException("Temporary connection failure!");
    }
}