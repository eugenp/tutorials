package org.baeldung.java;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * Java Random
 */
public class JavaRandomUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(JavaRandomUnitTest.class);

    // tests - random long

    /**
     * @see java.util.Random#nextLong()
     */
    @Test
    public void givenUsingPlainJava_whenGeneratingRandomLongUnbounded_thenCorrect() {
        final long generatedLong = new Random().nextLong();

        LOG.debug("generatedLong:{}", generatedLong);
    }

    /**
     * @see org.apache.commons.math3.random.RandomDataGenerator#getRandomGenerator()
     */
    @Test
    public void givenUsingApacheCommons_whenGeneratingRandomLongUnbounded_thenCorrect() {
        final long generatedLong = new RandomDataGenerator().getRandomGenerator()
            .nextLong();

        LOG.debug("{}", generatedLong);
    }

    /**
     * @see java.lang.Math#random() 之间的取值范围为：[0.0 , 1.0)
     * 生成[1,10)之间的随机整数
     */
    @Test
    public void givenUsingPlainJava_whenGeneratingRandomLongBounded_thenCorrect() {
        final long leftLimit = 1L;
        final long rightLimit = 10L;
        for(int i = 0 ; i < 20; i++){
            final long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            if(generatedLong == 10l){
                System.out.println("第" + i + "个位置得到10,generatedLong:{}" + generatedLong);
            }
            LOG.debug("{}", generatedLong);
        }
    }

    /**
     * @see org.apache.commons.math3.random.RandomDataGenerator#nextLong(long lower, long upper)
     * 注意区间范围，闭区间：[1,10]
     */
    @Test
    public void givenUsingApacheCommons_whenGeneratingRandomLongBounded_thenCorrect() {
        final long leftLimit = 1L;
        final long rightLimit = 10L;
        for(int i = 0;i < 100; i++){
            final long generatedLong = new RandomDataGenerator().nextLong(leftLimit, rightLimit);
            LOG.debug("{}", generatedLong);
        }
    }

    // tests - random int
    /**
     * 注意取值范围：前闭后开区间 ， [)
     */
    @Test
    public void givenUsingPlainJava_whenGeneratingRandomIntegerUnbounded_thenCorrect() {
        for(int i = 0; i < 10; i++){
            final int generatedInteger = new Random().nextInt(2);
            LOG.debug("{}", generatedInteger);
        }
    }

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomIntegerBounded_thenCorrect() {
        final int leftLimit = 1;
        final int rightLimit = 10;

        for(int i = 0 ; i < 100 ; i++){
            final int generatedInteger = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
            LOG.debug("{}", generatedInteger);
        }
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomIntegerUnbounded_thenCorrect() {
        final Integer generatedInteger = new RandomDataGenerator().getRandomGenerator()
            .nextInt();

        LOG.debug("{}", generatedInteger);
    }

    /**
     * [1,10)
     */
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
        for(int i = 0 ; i < 10 ; i++){
            final float generatedFloat = new Random().nextFloat();
            LOG.debug("{}", generatedFloat);
        }
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
        final float randomFloat = new RandomDataGenerator().getRandomGenerator().nextFloat();
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

        for(int i = 0;i < 10; i++){
            final double generatedDouble = new RandomDataGenerator().nextUniform(leftLimit, rightLimit);
            LOG.debug("{}", generatedDouble);
        }
    }

    // tests - random String

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect() {
        final byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        final String generatedString = new String(array, Charset.forName("UTF-8"));

        LOG.debug(generatedString);
    }

    /**
     * 生成[a , z]之间的随机字符串
     */
    @Test
    public void givenUsingPlainJava_whenGeneratingRandomStringBounded_thenCorrect() {
        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 10;
        final Random random = new Random();
        final StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            final int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        final String generatedString = buffer.toString();

        LOG.debug(generatedString);
    }

    /**
     * @see org.apache.commons.lang3.RandomStringUtils#random(int count)
     */
    @Test
    public void givenUsingApache_whenGeneratingRandomString_thenCorrect() {
        final String generatedString = RandomStringUtils.random(10);

        LOG.debug(generatedString);
    }

    /**
     * @see org.apache.commons.lang3.RandomStringUtils#randomAlphabetic(int count)
     * 生成随机字母
     */
    @Test
    public void givenUsingApache_whenGeneratingRandomAlphabeticString_thenCorrect() {
        final String generatedString = RandomStringUtils.randomAlphabetic(10);

        LOG.debug(generatedString);
    }

    /**
     * @see org.apache.commons.lang3.RandomStringUtils#randomAlphanumeric(int count)
     * 生成随机字母数字
     */
    @Test
    public void givenUsingApache_whenGeneratingRandomAlphanumericString_thenCorrect() {
        final String generatedString = RandomStringUtils.randomAlphanumeric(10);

        LOG.debug(generatedString);
    }

    /**
     * @see org.apache.commons.lang3.RandomStringUtils#random(int count, boolean letters, boolean numbers)
     * 可以根据实际需求，生成随机字母串、生成随机数字串、生成随机字母数字。
     */
    @Test
    public void givenUsingApache_whenGeneratingRandomStringBounded_thenCorrect() {
        final int length = 10;
        final boolean useLetters = true;
        final boolean useNumbers = false;
        final String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        LOG.debug(generatedString);
    }

}
