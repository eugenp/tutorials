package com.baeldung.springai.vectorstore.oracle;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

class QuoteFetcher {

    private static final String BASE_URL = "https://api.breakingbadquotes.xyz/v1/quotes/";
    private static final int DEFAULT_COUNT = 150;

    static List<Quote> fetch() {
        return fetch(DEFAULT_COUNT);
    }

    static List<Quote> fetch(int count) {
        return RestClient
            .create()
            .get()
            .uri(URI.create(BASE_URL + count))
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});
    }

}