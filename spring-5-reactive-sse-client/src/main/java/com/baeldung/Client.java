package com.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Client {
   static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {

        SpringApplication.run(Client.class);
        getData();
    }

    private static WebClient getWebClient() {
        return WebClient.create("http://localhost:8080");
    }

    public static void getData() {
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
