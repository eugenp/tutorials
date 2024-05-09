package com.baeldung.fluxmapandondonext;

import reactor.core.publisher.Flux;

public class DoOnNext {

    public static Flux<Integer> number_service() {
        return Flux.range(5, 5);
    }

    public static Flux<Integer> number_service_range() {
        return Flux.range(10, 5);
    }
}
