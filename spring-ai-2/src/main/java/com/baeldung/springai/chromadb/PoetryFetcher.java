package com.baeldung.springai.chromadb;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

public class PoetryFetcher {

    private static final String BASE_URL = "https://poetrydb.org/author/";
    private static final String DEFAULT_AUTHOR_NAME = "Shakespeare";

    public static List<Poem> fetch() {
        return fetch(DEFAULT_AUTHOR_NAME);
    }

    public static List<Poem> fetch(String authorName) {
        return RestClient
            .create()
            .get()
            .uri(URI.create(BASE_URL + authorName))
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});
    }

}