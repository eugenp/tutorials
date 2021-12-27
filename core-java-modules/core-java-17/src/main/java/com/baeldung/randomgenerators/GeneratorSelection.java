package com.baeldung.randomgenerators;

import java.util.random.RandomGenerator;

public class GeneratorSelection {

    public static int getRandomInt(RandomGenerator generator, int bound) {
        return generator.nextInt(bound);
    }

    public static RandomGenerator getDefaultGenerator() {
        return RandomGenerator.getDefault();
    }

    public static RandomGenerator getSpecificGenerator(String name) {
        return RandomGenerator.of(name);
    }

}
