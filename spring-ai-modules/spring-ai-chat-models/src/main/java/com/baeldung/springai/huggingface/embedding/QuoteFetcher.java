package com.baeldung.springai.huggingface.embedding;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

public class QuoteFetcher {

    private static final String BASE_URL = "https://quoteslate.vercel.app";
    private static final String API_PATH = "/api/quotes/random";
    private static final int DEFAULT_COUNT = 50;

    public static List<Quote> fetch() {
        return RestClient
            .create(BASE_URL)
            .get()
            .uri(uriBuilder ->
                uriBuilder
                    .path(API_PATH)
                    .queryParam("count", DEFAULT_COUNT)
                    .build())
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});
    }

}