package com.baeldung.webflux.threadstarvation;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class ThreadStarvationManualTest {
    private static final Logger LOG = LogManager.getLogger(ThreadStarvationApp.class);
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(new URI("http://localhost:8080/blocking"))
            .build();

        IntStream.range(0, 200)
            .forEach(__ -> pool.submit(() -> {
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(LOG::info);
            }));

        pool.awaitTermination(60, TimeUnit.SECONDS);
    }
}
