package com.baeldung.jmxterm;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumbergenerator {

    private static final int MIN = 0;
    private static final int MAX = 10;

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private RandomNumbergenerator() {
    }
    public static int generateRandomNumber() {
        return RANDOM.nextInt(MIN, MAX + 1);
    }

}
