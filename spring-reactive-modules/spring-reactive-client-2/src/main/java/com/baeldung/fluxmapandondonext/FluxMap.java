package com.baeldung.fluxmapandondonext;

import reactor.core.publisher.Flux;

public class FluxMap {

    public static Flux<Integer> numerical_values() {
        return Flux.range(50, 10);
    }

}
