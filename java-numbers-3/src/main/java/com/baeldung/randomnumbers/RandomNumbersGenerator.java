package com.baeldung.randomnumbers;

import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.apache.commons.math3.random.RandomDataGenerator;

import it.unimi.dsi.util.XoRoShiRo128PlusRandom;

public class RandomNumbersGenerator {

    public Integer generateRandomWithMathRandom(int max, int min) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Integer generateRandomWithNextInt() {
        Random random = new Random();
        int randomWithNextInt = random.nextInt();
        return randomWithNextInt;
    }

    public Integer generateRandomWithNextIntWithinARange(int min, int max) {
        Random random = new Random();
        int randomWintNextIntWithinARange = random.nextInt(max - min) + min;
        return randomWintNextIntWithinARange;
    }

    public IntStream generateRandomUnlimitedIntStream() {
        Random random = new Random();
        IntStream unlimitedIntStream = random.ints();
        return unlimitedIntStream;
    }

    public IntStream generateRandomLimitedIntStream(long streamSize) {
        Random random = new Random();
        IntStream limitedIntStream = random.ints(streamSize);
        return limitedIntStream;
    }

    public IntStream generateRandomLimitedIntStreamWithinARange(int min, int max, long streamSize) {
        Random random = new Random();
        IntStream limitedIntStreamWithinARange = random.ints(streamSize, min, max);
        return limitedIntStreamWithinARange;
    }

    public Integer generateRandomWithThreadLocalRandom() {
        int randomWithThreadLocalRandom = ThreadLocalRandom.current()
            .nextInt();
        return randomWithThreadLocalRandom;
    }

    public Integer generateRandomWithThreadLocalRandomInARange(int min, int max) {
        int randomWithThreadLocalRandomInARange = ThreadLocalRandom.current()
            .nextInt(min, max);
        return randomWithThreadLocalRandomInARange;
    }

    public Integer generateRandomWithThreadLocalRandomFromZero(int max) {
        int randomWithThreadLocalRandomFromZero = ThreadLocalRandom.current()
            .nextInt(max);
        return randomWithThreadLocalRandomFromZero;
    }

    public Integer generateRandomWithSplittableRandom(int min, int max) {
        SplittableRandom splittableRandom = new SplittableRandom();
        int randomWithSplittableRandom = splittableRandom.nextInt(min, max);
        return randomWithSplittableRandom;
    }

    public IntStream generateRandomWithSplittableRandomLimitedIntStreamWithinARange(int min, int max, long streamSize) {
        SplittableRandom splittableRandom = new SplittableRandom();
        IntStream limitedIntStreamWithinARangeWithSplittableRandom = splittableRandom.ints(streamSize, min, max);
        return limitedIntStreamWithinARangeWithSplittableRandom;
    }

    public Integer generateRandomWithSecureRandom() {
        SecureRandom secureRandom = new SecureRandom();
        int randomWithSecureRandom = secureRandom.nextInt();
        return randomWithSecureRandom;
    }

    public Integer generateRandomWithSecureRandomWithinARange(int min, int max) {
        SecureRandom secureRandom = new SecureRandom();
        int randomWithSecureRandomWithinARange = secureRandom.nextInt(max - min) + min;
        return randomWithSecureRandomWithinARange;
    }

    public Integer generateRandomWithRandomDataGenerator(int min, int max) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        int randomWithRandomDataGenerator = randomDataGenerator.nextInt(min, max);
        return randomWithRandomDataGenerator;
    }

    public Integer generateRandomWithXoRoShiRo128PlusRandom(int min, int max) {
        XoRoShiRo128PlusRandom xoroRandom = new XoRoShiRo128PlusRandom();
        int randomWithXoRoShiRo128PlusRandom = xoroRandom.nextInt(max - min) + min;
        return randomWithXoRoShiRo128PlusRandom;
    }

}
