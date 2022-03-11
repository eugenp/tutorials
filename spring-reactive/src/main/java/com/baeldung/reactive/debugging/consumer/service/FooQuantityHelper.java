package com.baeldung.reactive.debugging.consumer.service;

import com.baeldung.reactive.debugging.consumer.model.Foo;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;

public class FooQuantityHelper {

    public static Flux<Foo> processFooReducingQuantity(Flux<Foo> flux) {
        flux = flux.map(foo -> {
            Integer result;
            Integer random = ThreadLocalRandom.current()
                .nextInt(0, 90);
            result = (random == 0) ? result = 0 : foo.getQuantity() + 2;
            foo.setQuantity(result);
            return foo;
        });
        return divideFooQuantity(flux);
    }

    public static Flux<Foo> divideFooQuantity(Flux<Foo> flux) {
        return flux.map(foo -> {
            Integer result = Math.round(5 / foo.getQuantity());
            foo.setQuantity(result);
            return foo;
        });
    }

}
