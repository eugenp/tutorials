package com.baeldung.springai.mcp.test;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExchangeRateService {

    private static final String FRANKFURTER_URL = "https://api.frankfurter.dev/v1/latest?base={base}";

    private final RestClient restClient;

    public ExchangeRateService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public ExchangeRateResponse getLatestExchangeRate(String base) {
        if (base == null || base.isBlank()) {
            throw new IllegalArgumentException("base is required");
        }
        return restClient.get()
            .uri(FRANKFURTER_URL, base.trim().toUpperCase())
            .retrieve()
            .body(ExchangeRateResponse.class);
    }
}
