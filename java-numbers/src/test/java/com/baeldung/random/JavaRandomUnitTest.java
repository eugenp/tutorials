package com.baeldung.random;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class JavaRandomUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(JavaRandomUnitTest.class);

    // tests - random long

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomLongUnbounded_thenCorrect() {
        final long generatedLong = new Random().nextLong();

        LOG.debug("{}", generatedLong);
    }

    @Test
    public void givenUsingApacheCommons_whenGeneratingRandomLongUnbounded_thenCorrect() {
        final long generatedLong = new RandomDataGenerator().getRandomGenerator()
            .nextLong();

        LOG.debug("{}", generatedLong);
    }

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomLongBounded_thenCorrect() {
        final long leftLimit = 1L;
        final long rightLimit = 10L;
        final long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        LOG.debug("{}", generatedLong);
    }

    @Test
    public void givenUsingApacheCommons_whenGeneratingRandomLongBounded_thenCorrect() {
        final long leftLimit = 10L;
        final long rightLimit = 100L;
        final long generatedLong = new RandomDataGenerator().nextLong(leftLimit, rightLimit);

        LOG.debug("{}", generatedLong);
    }

    // tests - random int

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomIntegerUnbounded_thenCorrect() {
        final int generatedInteger = new Random().nextInt();

        LOG.debug("{}", generatedInteger);
    }

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomIntegerBounded_thenCorrect() {
        final int leftLimit = 1;
        final int rightLimit = 10;
        final int generatedInteger = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));

        LOG.debug("{}", generatedInteger);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomIntegerUnbounded_thenCorrect() {
        final Integer generatedInteger = new RandomDataGenerator().getRandomGenerator()
            .nextInt();

        LOG.debug("{}", generatedInteger);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomIntegerBounded_thenCorrect() {
        final int leftLimit = 1;
        final int rightLimit = 10;
        final int generatedInteger = new RandomDataGenerator().nextInt(leftLimit, rightLimit);

        LOG.debug("{}", generatedInteger);
    }

    // tests - random float

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomFloatUnbouned_thenCorrect() {
        final float generatedFloat = new Random().nextFloat();

        LOG.debug("{}", generatedFloat);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomFloatUnbounded_thenCorrect() {
        final float generatedFloat = new RandomDataGenerator().getRandomGenerator()
            .nextFloat();

        LOG.debug("{}", generatedFloat);
    }

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomFloatBouned_thenCorrect() {
        final float leftLimit = 1F;
        final float rightLimit = 10F;
        final float generatedFloat = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);

        LOG.debug("{}", generatedFloat);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomFloatBounded_thenCorrect() {
        final float leftLimit = 1F;
        final float rightLimit = 10F;
        final float randomFloat = new RandomDataGenerator().getRandomGenerator()
            .nextFloat();
        final float generatedFloat = leftLimit + randomFloat * (rightLimit - leftLimit);

        LOG.debug("{}", generatedFloat);
    }

    // tests - random double

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomDoubleUnbounded_thenCorrect() {
        final double generatedDouble = Math.random();

        LOG.debug("{}", generatedDouble);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomDoubleUnbounded_thenCorrect() {
        final double generatedDouble = new RandomDataGenerator().getRandomGenerator()
            .nextDouble();

        LOG.debug("{}", generatedDouble);
    }

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomDoubleBounded_thenCorrect() {
        final double leftLimit = 1D;
        final double rightLimit = 10D;
        final double generatedDouble = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);

        LOG.debug("{}", generatedDouble);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomDoubleBounded_thenCorrect() {
        final double leftLimit = 1D;
        final double rightLimit = 100D;
        final double generatedDouble = new RandomDataGenerator().nextUniform(leftLimit, rightLimit);

        LOG.debug("{}", generatedDouble);
    }

}
