package com.baeldung.jmxterm;

import java.util.Random;

public class RandomNumbergenerator {

    private static final int MIN = 0;
    private static final int MAX = 10;

    private static final Random RANDOM = new Random();

    private RandomNumbergenerator() {
    }
    public static int generateRandomNumber() {
        return RANDOM.nextInt(MIN, MAX + 1);
    }

}
