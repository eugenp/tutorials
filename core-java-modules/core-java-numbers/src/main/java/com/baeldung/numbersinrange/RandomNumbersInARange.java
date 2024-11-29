package com.baeldung.numbersinrange;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumbersInARange {

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
          .findFirst()
          .getAsInt();
    }

    public int getRandomNumberUsingThreadLocalRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
