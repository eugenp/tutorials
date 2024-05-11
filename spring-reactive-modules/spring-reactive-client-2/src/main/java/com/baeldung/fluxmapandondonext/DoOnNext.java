package com.baeldung.fluxmapandondonext;

import reactor.core.publisher.Flux;

public class DoOnNext {

    public Flux<Integer> number_service_range() {
        return Flux.just(10, 11, 12, 13, 14);
    }
}
