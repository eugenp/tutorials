package com.baeldung.spf4j.aspects;

import java.util.Random;

import org.spf4j.annotations.PerformanceMonitor;

public class App {
    
    public static void main(String[] args) throws InterruptedException {
        Spf4jConfig.initialize();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            long numberToCheck = random.nextInt(999_999_999 - 100_000_000 + 1) + 100_000_000;
            isPrimeNumber(numberToCheck);
        }
        System.exit(0);
    }

    @PerformanceMonitor(warnThresholdMillis = 1, errorThresholdMillis = 100, recorderSource = Spf4jConfig.RecorderSourceForIsPrimeNumber.class)
    public static boolean isPrimeNumber(long number) {
        for (long i = 2; i <= number / 2; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

}
