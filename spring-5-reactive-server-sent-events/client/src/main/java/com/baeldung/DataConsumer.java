package com.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class DataConsumer {
    Logger logger = LoggerFactory.getLogger(DataConsumer.class);

    private WebClient getWebClient() {
        return WebClient.create("http://localhost:8080");
    }

    public void getData() {
        WebClient client = getWebClient();

        client
          .get()
          .uri("/data")
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(Long.class)
          .map(s -> String.valueOf(s))
          .subscribe(msg -> logger.info(msg));
    }
}

