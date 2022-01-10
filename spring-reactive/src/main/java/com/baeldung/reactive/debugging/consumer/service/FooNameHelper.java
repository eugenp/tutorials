package com.baeldung.reactive.debugging.consumer.service;

import com.baeldung.reactive.debugging.consumer.model.Foo;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;

public class FooNameHelper {

    public static Flux<Foo> concatAndSubstringFooName(Flux<Foo> flux) {
        flux = concatFooName(flux);
        flux = substringFooName(flux);
        return flux;
    }

    public static Flux<Foo> concatFooName(Flux<Foo> flux) {
        flux = flux.map(foo -> {
            String processedName = null;
            Integer random = ThreadLocalRandom.current()
                .nextInt(0, 80);
            processedName = (random != 0) ? foo.getFormattedName() : foo.getFormattedName() + "-bael";
            foo.setFormattedName(processedName);
            return foo;
        });
        return flux;
    }

    public static Flux<Foo> substringFooName(Flux<Foo> flux) {
        return flux.map(foo -> {
            String processedName;
            Integer random = ThreadLocalRandom.current()
                .nextInt(0, 100);

            processedName = (random == 0) ? foo.getFormattedName()
                .substring(10, 15)
                : foo.getFormattedName()
                    .substring(0, 5);

            foo.setFormattedName(processedName);
            return foo;
        });
    }

}
