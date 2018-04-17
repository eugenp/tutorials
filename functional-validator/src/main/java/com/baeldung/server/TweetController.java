package com.baeldung.server;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@RestController
public class TweetController {

    @Autowired
    private Validator validator;

    private static List<Tweet> tweets = new ArrayList<Tweet>();

    static {
        Tweet tweet = Tweet.createTweet("1", "default tweet");
        tweets.add(tweet);
    }

    @Bean
    public RouterFunction<ServerResponse> createTweetRoute(TweetController controller) {
        return route(GET("/tweets/{id}"), controller::getTweetById).and(route(POST("/tweets/addTweet"), controller::addTweet));
    }

    private Mono<ServerResponse> getTweetById(ServerRequest request) {
        String id = request.pathVariable("id");
        Tweet tweet = Tweet.createTweet(String.valueOf(System.currentTimeMillis()), "Tweet not available");

        for (Tweet currentTweet : tweets) {
            if (currentTweet.getId()
                .equals(id)) {
                tweet = currentTweet;
            }
        }
        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(tweet), Tweet.class);
    }

    private Mono<ServerResponse> addTweet(ServerRequest request) {
        return validateTweet(body -> {

            Mono<Tweet> monoTweet = body.map(newTweet -> Tweet.createTweet(newTweet.getId(), newTweet.getText()));
            tweets.add(monoTweet.block());
            return ServerResponse.ok()
                .body(Mono.just("Tweet Successfully Added"), String.class);

        }, request, Tweet.class);
    }

    private <T> Mono<ServerResponse> validateTweet(Function<Mono<T>, Mono<ServerResponse>> block, ServerRequest request, Class<T> bodyClass) {

        return request.bodyToMono(bodyClass)
            .flatMap(body -> validator.validate(body)
                .isEmpty() ? block.apply(Mono.just(body))
                    : ServerResponse.unprocessableEntity()
                        .build());
    }

}
