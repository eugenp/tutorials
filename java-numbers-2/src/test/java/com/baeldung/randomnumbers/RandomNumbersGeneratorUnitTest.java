package com.baeldung.randomnumbers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.Test;

public class RandomNumbersGeneratorUnitTest {
    
    private static final int MIN_RANGE = 10;
    private static final int MAX_RANGE = 100;
    private static final long STREAM_SIZE = 15;
    
    @Test
    public void whenGenerateRandomWithMathRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithMathRandom(MIN_RANGE, MAX_RANGE);
        assertNotNull(random);
        assertTrue(MIN_RANGE <= random);
    }
    
    @Test
    public void whenGenerateRandomWithNextInt_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithNextInt();
        assertNotNull(random);
    }
    
    @Test
    public void whenGenerateRandomWithNextIntWithinARange_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithNextIntWithinARange(MIN_RANGE, MAX_RANGE);
        assertNotNull(random);
        assertTrue(MIN_RANGE <= random);
    }
    
    @Test
    public void whenGenerateRandomUnlimitedIntStream_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        IntStream stream = generator.generateRandomUnlimitedIntStream();
        assertNotNull(stream);
        Integer random = stream.findFirst().getAsInt();
        assertNotNull(random);
    }
    
    @Test
    public void whenGenerateRandomLimitedIntStream_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        IntStream stream = generator.generateRandomLimitedIntStream(STREAM_SIZE);
        assertNotNull(stream);
        assertEquals(STREAM_SIZE, stream.count());
    }
    
    @Test
    public void whenGenerateRandomLimitedIntStreamWithinARange_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        IntStream stream = generator.generateRandomLimitedIntStreamWithinARange(MIN_RANGE, MAX_RANGE, STREAM_SIZE);
        assertNotNull(stream);
        assertEquals(STREAM_SIZE, stream.count());
    }
    
    @Test
    public void whenGenerateRandomWithThreadLocalRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithThreadLocalRandom();
        assertNotNull(random);
    }
    
    @Test
    public void whenGenerateRandomWithThreadLocalRandomInARange_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithThreadLocalRandomInARange(MIN_RANGE, MAX_RANGE);
        assertNotNull(random);
        assertTrue(MIN_RANGE <= random);
    }
    
    @Test
    public void whenGenerateRandomWithThreadLocalRandomFromZero_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithThreadLocalRandomFromZero(MAX_RANGE);
        assertNotNull(random);
        assertTrue(0 <= random);
    }
    
    @Test
    public void whenGenerateRandomWithSplittableRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithSplittableRandom(MIN_RANGE, MAX_RANGE);
        assertNotNull(random);
        assertTrue(MIN_RANGE <= random);
    }
    
    @Test
    public void whenGenerateRandomWithSecureRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithSecureRandom();
        assertNotNull(random);        
    }
    
    @Test
    public void whenGenerateRandomWithRandomDataGenerator_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithRandomDataGenerator(MIN_RANGE, MAX_RANGE);
        assertNotNull(random);
        assertTrue(MIN_RANGE <= random);
    }
    
    @Test
    public void whenGenerateRandomWithXoRoShiRo128PlusRandom_returnsSuccessfully() {
        RandomNumbersGenerator generator = new RandomNumbersGenerator();
        Integer random = generator.generateRandomWithXoRoShiRo128PlusRandom(MIN_RANGE, MAX_RANGE);
        assertNotNull(random);
        assertTrue(MIN_RANGE <= random);
    }

}
