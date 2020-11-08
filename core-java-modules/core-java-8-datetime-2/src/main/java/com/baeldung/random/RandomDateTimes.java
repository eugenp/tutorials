package com.baeldung.random;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateTimes {

    public static Instant timestamp() {
        return Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt());
    }

    public static Instant between(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random);
    }

    public static Instant after(Instant startInclusive) {
        return between(startInclusive, Instant.MAX);
    }

    public static Instant before(Instant upperExclusive) {
        return between(Instant.MIN, upperExclusive);
    }
}
