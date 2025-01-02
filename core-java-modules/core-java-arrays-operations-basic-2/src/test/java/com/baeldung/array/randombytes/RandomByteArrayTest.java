package com.baeldung.array.randombytes;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomByteArrayTest {

    int SIZE = 16;

    @Test
    public void givenSizeWhenGenerateUsingRandomThenOK() {
        byte[] byteArray = new byte[SIZE];
        Random random = new Random();
        random.nextBytes(byteArray);

        assertEquals(SIZE, byteArray.length);
    }

    @Test
    public void givenSizeWhenGenerateUsingSecureRandomThenOK() {
        byte[] byteArray = new byte[SIZE];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(byteArray);

        assertEquals(SIZE, byteArray.length);
    }

    @Test
    public void givenSizeWhenGenerateUsingRandomUtilsThenOK() {
        byte[] byteArray = RandomUtils.nextBytes(SIZE);

        assertEquals(SIZE, byteArray.length);
    }

    @Test
    public void givenSizeWhenGenerateUsingUniformRandomProviderThenOK() {
        byte[] byteArray = new byte[SIZE];
        UniformRandomProvider randomProvider = RandomSource.XO_RO_SHI_RO_128_PP.create();
        randomProvider.nextBytes(byteArray);

        assertEquals(SIZE, byteArray.length);
    }

}
