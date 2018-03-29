package com.baeldung.webflux;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
class TweetController {

    @GetMapping("/tweets/{id}")
    public Mono<Tweet> getTweetById(@PathVariable (value="id") String tweetId) {
        return Mono.just(new Tweet(tweetId, "My Test Tweet"));
    }

    @GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE, value = "/tweets")
    public Flux<Tweet> getTweets() {
        Flux<Tweet> tweetFlux = Flux.fromStream(
                Stream.generate(
                    () -> new Tweet("" + System.currentTimeMillis(), "Test Tweet")
                    ));
        Flux<Long> timeFlux = Flux.interval(Duration.ofSeconds(1));
        
        return Flux.zip(tweetFlux, timeFlux).map(t -> t.getT1());
    }
    
}
