package com.baeldung.webclient;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class WebController {

    private static final int DEFAULT_PORT = 8080;

    public static final String SLOW_SERVICE_TWEETS_ENDPOINT_NAME = "/slow-service-tweets";

    @Setter
    private int serverPort = DEFAULT_PORT;

    @Autowired
    private TweetsFeignClient tweetsFeignClient;

    @GetMapping("/tweets-blocking")
    public List<Tweet> getTweetsBlocking() {
        log.info("Starting BLOCKING Controller!");
        final URI uri = URI.create(getSlowServiceBaseUri());

        List<Tweet> result = tweetsFeignClient.getTweetsBlocking(uri);
        result.forEach(tweet -> log.info(tweet.toString()));
        log.info("Exiting BLOCKING Controller!");
        return result;
    }

    @GetMapping(value = "/tweets-non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> getTweetsNonBlocking() {
        log.info("Starting NON-BLOCKING Controller!");
        Flux<Tweet> tweetFlux = WebClient.create()
          .get()
          .uri(getSlowServiceBaseUri() + SLOW_SERVICE_TWEETS_ENDPOINT_NAME)
          .retrieve()
          .bodyToFlux(Tweet.class);

        tweetFlux.subscribe(tweet -> log.info(tweet.toString()));
        log.info("Exiting NON-BLOCKING Controller!");
        return tweetFlux;
    }

    private String getSlowServiceBaseUri() {
        return "http://localhost:" + serverPort;
    }

}
