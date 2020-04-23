package com.baeldung.awsappsync;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AppSyncClientHelper {

    static String apiUrl = "https://m4i3b6icrrb7livfbypfspiifi.appsync-api.us-east-2.amazonaws.com";
    static String apiKey = "da2-es2s6oj4mzhbxk5yu26ss2ruj4";
    static String API_KEY_HEADER = "x-api-key";

    public static WebClient.ResponseSpec getResponseBodySpec(Map<String, Object> requestBody) {
        return WebClient
                .builder()
                .baseUrl(apiUrl)
                .defaultHeader(API_KEY_HEADER, apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build()
                .method(HttpMethod.POST)
                .uri("/graphql")
                .body(BodyInserters.fromValue(requestBody))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();
    }

}
