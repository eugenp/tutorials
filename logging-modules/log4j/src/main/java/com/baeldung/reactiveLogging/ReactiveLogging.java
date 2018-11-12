package com.baeldung.reactiveLogging;

import reactor.core.publisher.Flux;

public class ReactiveLogging {
    
    public static void main(String[] args) {
        Flux<Integer> reactiveStream = Flux.range(1, 5)
            .log()
            .take(3);

        reactiveStream.subscribe();
    }

}
