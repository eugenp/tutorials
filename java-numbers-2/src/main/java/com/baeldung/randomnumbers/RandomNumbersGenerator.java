package com.baeldung.randomnumbers;

import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.apache.commons.math3.random.RandomDataGenerator;

import it.unimi.dsi.util.XoRoShiRo128PlusRandom;

public class RandomNumbersGenerator {

    public void randomNumbers() {
        int min = 10;
        int max = 100;
        long streamSize = 100;
        int randomWithMathRandom = (int) ((Math.random() * (max - min)) + min);
        Random random = new Random();
        int randomWithNextInt = random.nextInt();
        int randomWintNextIntWithinARange = random.nextInt(max - min) + min;
        IntStream unlimitedIntStream = random.ints();
        IntStream limitedIntStream = random.ints(streamSize);
        IntStream limitedIntStreamWithinARange = random.ints(streamSize, min, max);
        int randomWithThreadLocalRandom = ThreadLocalRandom.current()
            .nextInt();
        int randomWithThreadLocalRandomInARange = ThreadLocalRandom.current()
            .nextInt(min, max);
        int randomWithThreadLocalRandomFromZero = ThreadLocalRandom.current()
            .nextInt(max);
        IntStream streamWithThreadLocalRandom = ThreadLocalRandom.current()
            .ints();
        SplittableRandom splittableRandom = new SplittableRandom();
        int randomWithSplittableRandom = splittableRandom.nextInt(min, max);
        IntStream streamWithSplittableRandom = splittableRandom.ints();
        SecureRandom secureRandom = new SecureRandom();
        int randomWithSecureRandom = secureRandom.nextInt();
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        int randomWithRandomDataGenerator = randomDataGenerator.nextInt(min, max);
        XoRoShiRo128PlusRandom xoroRandom = new XoRoShiRo128PlusRandom();
        int randomWithXoRoShiRo128PlusRandom = xoroRandom.nextInt(max - min) + min;
    }

}
