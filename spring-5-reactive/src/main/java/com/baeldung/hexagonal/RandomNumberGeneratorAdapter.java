package com.baeldung.hexagonal;

import java.util.Random;

public class RandomNumberGeneratorAdapter implements RandomNumberGeneratorPort {

    @Override
    public Integer generateRandomNumber(Integer lowerLimit, Integer upperLimit) {
        Random r = new Random();
        return r.nextInt((upperLimit - lowerLimit) + 1) + lowerLimit;
    }

}
