package com.baeldung.probability;

import io.vavr.Lazy;

import java.util.SplittableRandom;
import java.util.function.Supplier;

public class RandomInvoker {
    private final Lazy<SplittableRandom> random = Lazy.of(SplittableRandom::new);

    public <T> T withProbability(Supplier<T> supplier1, Supplier<T> supplier2, int probability) {
        SplittableRandom random = this.random.get();
        if (random.nextInt(1, 101) <= probability) {
            return supplier1.get();
        } else {
            return supplier2.get();
        }
    }
}
