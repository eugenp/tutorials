package com.baeldung.reactor.completablefuturevsmono;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class MonoDemo {

    public static void main(String[] args) {
        Mono<String> reactiveMono = Mono.fromCallable(() -> {
                Thread.sleep(1000);
                return "Reactive Data";
            })
            .subscribeOn(Schedulers.boundedElastic());

        reactiveMono.subscribe(System.out::println);
    }
}