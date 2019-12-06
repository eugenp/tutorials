package com.baeldung.randomstrings;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Random;

public class RandomStringsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(RandomStringsUnitTest.class);

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect() {
        final byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        final String generatedString = new String(array, Charset.forName("UTF-8"));

        LOG.debug(generatedString);
    }

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

    @Test
    public void givenUsingApache_whenGeneratingRandomString_thenCorrect() {
        final String generatedString = RandomStringUtils.random(10);

        LOG.debug(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomAlphabeticString_thenCorrect() {
        final String generatedString = RandomStringUtils.randomAlphabetic(10);

        LOG.debug(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomAlphanumericString_thenCorrect() {
        final String generatedString = RandomStringUtils.randomAlphanumeric(10);

        LOG.debug(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomStringBounded_thenCorrect() {
        final int length = 10;
        final boolean useLetters = true;
        final boolean useNumbers = false;
        final String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        LOG.debug(generatedString);
    }

}
