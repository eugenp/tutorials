package com.baeldung.micrometer.tags.rest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
class SendDummyRequests {

    private static final RestClient client = RestClient.create("http://localhost:8080");

    private static final List<String> userAgents = List.of("Mozilla/5.0", "Chrome/124.0.0.0", "Safari/605.1.15", "Firefox/124.0", "Googlebot/2.1", "Opera/99.0",
        "Edge/124.0.0.0", "curl/8.0.1");

    @EventListener(ApplicationReadyEvent.class)
    void sendRequests() {
        IntStream.range(0, 100)
            .mapToObj(__ -> randomUserAgent())
            .parallel()
            .forEach(userAgent -> {
                sendHttpRequest("/api/dummy", userAgent);
                sendHttpRequest("/api/foo", userAgent);
            });
    }

    private static ResponseEntity<String> sendHttpRequest(String path, String userAgent) {
        return client.get()
            .uri(path)
            .header("User-Agent", userAgent)
            .retrieve()
            .toEntity(String.class);
    }

    private static String randomUserAgent() {
        return userAgents.get(ThreadLocalRandom.current()
            .nextInt(0, userAgents.size()));
    }

}
