package com.baeldung.cucumber_tags.service;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGeneratorService {

    public int generateRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}