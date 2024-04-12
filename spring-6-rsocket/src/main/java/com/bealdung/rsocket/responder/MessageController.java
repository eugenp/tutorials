package com.bealdung.rsocket.responder;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MessageController {

    @MessageMapping("MyDestination")
    public Mono<String> message(Mono<String> input) {
        return input.doOnNext(msg -> System.out.println("Request is:" + msg + ",Request!"))
            .map(msg -> msg + ",Response!");
    }

    @MessageMapping("Counter")
    public Flux<String> Counter() {
        return Flux.range(1, 10)
            .map(i -> "Count is: " + i);
    }

    @MessageMapping("Warning")
    public Mono<Void> Warning(Mono<String> error) {
        error.doOnNext(e -> System.out.println("warning is :" + e))
            .subscribe();
        return Mono.empty();
    }

    @MessageMapping("channel")
    public Flux<String> channel(Flux<String> input) {
        return input.doOnNext(i -> {
                System.out.println("Received message is : " + i);
            })
            .map(m -> m.toUpperCase())
            .doOnNext(r -> {
                System.out.println("RESPONSE IS :" + r);
            });
    }

}



