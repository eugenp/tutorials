package com.baeldung.reactive;

import com.baeldung.reactive.model.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class FooClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooClient.class);

    public static void main(String[] args) throws InterruptedException {
        WebClient
          .create("http://localhost:8080")
          .get()
          .uri("/someotherfoos")
          .accept(MediaType.APPLICATION_STREAM_JSON)
          .retrieve()
          .bodyToFlux(Foo.class)
          .subscribe(
            foo -> LOGGER.info("{}", foo),
            err -> LOGGER.error("An error occurred", err),
            () -> LOGGER.info("Flux terminated")
          );

        Thread.sleep(60000);
    }

}
