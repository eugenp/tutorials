package com.baeldung.cn1tutorial.util;

import java.util.Random;

/**
 * Generates identifiers for newly created activities.
 * <p>
 * The format is intentionally simple and human-readable because the app stores data locally and
 * does not need globally unique UUIDs.
 */
public final class IdGenerator {
    private static final Random RANDOM = new Random();
    private static int counter;

    /**
     * Utility class; do not instantiate.
     */
    private IdGenerator() {
    }

    /**
     * Builds a best-effort unique identifier from timestamp, counter and random suffix.
     *
     * @return generated activity id
     */
    public static synchronized String newId() {
        counter++;
        long now = System.currentTimeMillis();
        int random = Math.abs(RANDOM.nextInt());
        return "activity-" + now + "-" + counter + "-" + random;
    }
}
