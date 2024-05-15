package com.baeldung.randomgenerators;

import java.util.Random;

public class OldRandom {

    public static int getRandomInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

}
