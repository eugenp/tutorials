package com.baeldung.spf4j.core;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spf4j.perf.MeasurementRecorder;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        Spf4jConfig.initialize();
        MeasurementRecorder measurementRecorder = Spf4jConfig.getMeasurementRecorder(App.class + " isPrimeNumber");
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            long numberToCheck = random.nextInt(999_999_999 - 100_000_000 + 1) + 100_000_000;
            long startTime = System.currentTimeMillis();
            boolean isPrime = isPrimeNumber(numberToCheck);
            measurementRecorder.record(System.currentTimeMillis() - startTime);
            LOGGER.info("{}. {} is prime? {}", i + 1, numberToCheck, isPrime);
        }
        System.exit(0);
    }

    private static boolean isPrimeNumber(long number) {
        for (long i = 2; i <= number / 2; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

}
