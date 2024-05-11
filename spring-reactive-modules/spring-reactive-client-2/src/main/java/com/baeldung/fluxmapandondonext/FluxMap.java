package com.baeldung.fluxmapandondonext;

import reactor.core.publisher.Flux;

public class FluxMap {

    public Flux<Integer> numerical_values() {
        return Flux.just(50, 51, 52, 53, 54, 55, 56, 57, 58, 59);
    }

}
