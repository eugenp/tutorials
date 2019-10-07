package com.baeldung.webclient.clientcredentials.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientChonJob {

    Logger logger = LoggerFactory.getLogger(WebClientChonJob.class);

    private static final String RESOURCE_URI = "localhost:8082/spring-security-oauth-resource/foos/1";

    @Autowired
    private WebClient webClient;

    @Scheduled(fixedRate = 5000)
    public void logResourceServiceResponse() {

        webClient.get()
            .uri(RESOURCE_URI)
            .retrieve()
            .bodyToMono(String.class)
            .map(string -> "We retrieved the following resource using Client Credentials Grant Type: " + string)
            .subscribe(logger::info);
    }

}
