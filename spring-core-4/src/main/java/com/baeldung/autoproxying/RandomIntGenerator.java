package com.baeldung.autoproxying;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomIntGenerator {
    private final Random random = new Random();

    public int generate(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
