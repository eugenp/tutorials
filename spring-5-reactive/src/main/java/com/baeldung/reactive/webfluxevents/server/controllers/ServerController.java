package com.baeldung.reactive.webfluxevents.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/sse")
public class ServerController {

    private static Logger logger = LoggerFactory.getLogger(ServerController.class);

    @GetMapping("/sse-flux")
    public Flux<ServerSentEvent<String>> streamColdEvents() {
        return Flux.interval(Duration.ofSeconds(1))
          .doOnSubscribe(sub -> logger.info("Someone subscribed"))
          .map(seq -> {
              logger.info("sending event " + seq);
              return ServerSentEvent.<String>builder()
                .id(String.valueOf(seq))
                .event("EVENT: something COLD happened!")
                .data("{\"eventInfo\":\"This happened\",\"timeMillis\":" + System.currentTimeMillis() + "}")
                .build();
          });
    }
}
