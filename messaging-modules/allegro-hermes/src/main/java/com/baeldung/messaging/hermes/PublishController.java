package com.baeldung.messaging.hermes;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import pl.allegro.tech.hermes.client.HermesClient;
import pl.allegro.tech.hermes.client.HermesClientBuilder;
import pl.allegro.tech.hermes.client.HermesResponse;
import pl.allegro.tech.hermes.client.HermesSender;
import pl.allegro.tech.hermes.client.restclient.RestClientHermesSender;

@RestController
@RequestMapping("/publish")
public class PublishController {
    private static final Logger LOG = LoggerFactory.getLogger(PublishController.class);

    @Value("${hermes.url}")
    private String hermesUrl;

    @PostMapping
    public void publish() throws ExecutionException, InterruptedException {
        HermesSender hermesSender = new RestClientHermesSender(RestClient.create());

        HermesClient hermesClient = HermesClientBuilder.hermesClient(hermesSender)
            .withURI(URI.create(hermesUrl))
            .build();

        CompletableFuture<HermesResponse> result =
            hermesClient.publishJSON("com.baeldung.hermes.testing", "{\"hello\": 1}");

        HermesResponse hermesResponse = result.join();

        LOG.info("Result of publishing message: {}", hermesResponse);
    }
}
