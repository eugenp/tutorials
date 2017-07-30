package org.baeldung.util;

import java.util.Random;

public final class IDUtil {

    private IDUtil() {
        throw new AssertionError();
    }

    // API

    public static String randomPositiveLongAsString() {
        return Long.toString(randomPositiveLong());
    }

    public static String randomNegativeLongAsString() {
        return Long.toString(randomNegativeLong());
    }

    public static long randomPositiveLong() {
        long id = new Random().nextLong() * 10000;
        id = (id < 0) ? (-1 * id) : id;
        return id;
    }

    private static long randomNegativeLong() {
        long id = new Random().nextLong() * 10000;
        id = (id > 0) ? (-1 * id) : id;
        return id;
    }

}
