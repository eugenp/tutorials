package com.baeldung.healthapp;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.faunadb.client.FaunaClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConfigurationProperties
@Component
public class FaunaClients {

    private final Map<String, String> faunaConnections = new HashMap<>();
    private final Map<String, String> faunaSecrets = new HashMap<>();

    public FaunaClient getFaunaClient(String region) throws MalformedURLException {

        String faunaUrl = faunaConnections.get(region);
        String faunaSecret = faunaSecrets.get(region);

        log.info("Creating Fauna Client for Region:{} with URL:{}", region, faunaUrl);

        return FaunaClient.builder()
            .withEndpoint(faunaUrl)
            .withSecret(faunaSecret)
            .build();
    }

    public Map<String, String> getFaunaConnections() {
        return faunaConnections;
    }

    public Map<String, String> getFaunaSecrets() {
        return faunaSecrets;
    }
}
