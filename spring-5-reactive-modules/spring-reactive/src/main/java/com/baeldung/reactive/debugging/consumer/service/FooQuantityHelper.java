package com.baeldung.reactive.debugging.consumer.service;

import com.baeldung.reactive.debugging.consumer.model.Foo;

import java.util.concurrent.ThreadLocalRandom;

public class FooQuantityHelper {

    public static Foo processFooReducingQuantity(Foo foo) {
        int random = ThreadLocalRandom.current().nextInt(0, 90);
        int result = (random == 0) ? 0 : foo.getQuantity() + 2;
        foo.setQuantity(result);

        return divideFooQuantity(foo);
    }

    public static Foo divideFooQuantity(Foo foo) {

        Integer result = (int) Math.round(5.0 / foo.getQuantity());
        foo.setQuantity(result);
        return foo;
    }

}
