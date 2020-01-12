package com.baeldung.randomnumbers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.Test;

public class RandomNumbersGeneratorUnitTest {

    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 10;
    private static final int ITERATIONS = 50;
    private static final long STREAM_SIZE = 50;

    @Test
    public void whenGenerateRandomWithMathRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumer = generator.generateRandomWithMathRandom(MIN_RANGE, MAX_RANGE);
            assertTrue(isInRange(randomNumer, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
    }

    @Test
    public void whenGenerateRandomWithNextInt_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithNextInt();
            assertTrue(isInRange(randomNumber, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
    }

    @Test
    public void whenGenerateRandomWithNextIntWithinARange_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithNextIntWithinARange(MIN_RANGE, MAX_RANGE);
            assertTrue(isInRange(randomNumber, MIN_RANGE, MAX_RANGE));
        }
    }

    @Test
    public void whenGenerateRandomUnlimitedIntStream_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        IntStream stream = generator.generateRandomUnlimitedIntStream();
        assertNotNull(stream);
        Integer randomNumber = stream.findFirst()
            .getAsInt();
        assertNotNull(randomNumber);
        assertTrue(isInRange(randomNumber, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void whenGenerateRandomLimitedIntStream_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        generator.generateRandomLimitedIntStream(STREAM_SIZE)
            .forEach(randomNumber -> assertTrue(isInRange(randomNumber, Integer.MIN_VALUE, Integer.MAX_VALUE)));
    }

    @Test
    public void whenGenerateRandomLimitedIntStreamWithinARange_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        generator.generateRandomLimitedIntStreamWithinARange(MIN_RANGE, MAX_RANGE, STREAM_SIZE)
            .forEach(randomNumber -> assertTrue(isInRange(randomNumber, MIN_RANGE, MAX_RANGE)));
    }

    @Test
    public void whenGenerateRandomWithThreadLocalRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithThreadLocalRandom();
            assertTrue(isInRange(randomNumber, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
    }

    @Test
    public void whenGenerateRandomWithThreadLocalRandomInARange_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithThreadLocalRandomInARange(MIN_RANGE, MAX_RANGE);
            assertTrue(isInRange(randomNumber, MIN_RANGE, MAX_RANGE));
        }
    }

    @Test
    public void whenGenerateRandomWithThreadLocalRandomFromZero_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithThreadLocalRandomFromZero(MAX_RANGE);
            assertTrue(isInRange(randomNumber, 0, MAX_RANGE));
        }
    }

    @Test
    public void whenGenerateRandomWithSplittableRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithSplittableRandom(MIN_RANGE, MAX_RANGE);
            assertTrue(isInRange(randomNumber, MIN_RANGE, MAX_RANGE));
        }
    }

    @Test
    public void whenGenerateRandomWithSecureRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithSecureRandom();
            assertTrue(isInRange(randomNumber, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
    }

    @Test
    public void whenGenerateRandomWithRandomDataGenerator_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithRandomDataGenerator(MIN_RANGE, MAX_RANGE);
            // RandomDataGenerator top is inclusive
            assertTrue(isInRange(randomNumber, MIN_RANGE, MAX_RANGE + 1));
        }
    }

    @Test
    public void whenGenerateRandomWithXoRoShiRo128PlusRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        for (int i = 0; i < ITERATIONS; i++) {
            int randomNumber = generator.generateRandomWithXoRoShiRo128PlusRandom(MIN_RANGE, MAX_RANGE);
            assertTrue(isInRange(randomNumber, MIN_RANGE, MAX_RANGE));
        }
    }

    private boolean isInRange(int number, int min, int max) {
        return min <= number && number < max;
    }

}
