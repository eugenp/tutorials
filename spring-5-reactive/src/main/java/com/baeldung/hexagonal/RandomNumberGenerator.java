package com.baeldung.hexagonal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomNumberGenerator {

    private static final Logger log = LoggerFactory.getLogger(RandomNumberGenerator.class);

    public static void main(String[] args) {
        Integer min = 1;
        Integer max = 10;
        RandomNumberGeneratorPort randomNumber = new RandomNumberGeneratorAdapter();
        Integer result = randomNumber.generateRandomNumber(min, max);
        log.info("Given minimum={} and maximum={} as range, {} was randomly generated.", min, max, result);
    }

}
