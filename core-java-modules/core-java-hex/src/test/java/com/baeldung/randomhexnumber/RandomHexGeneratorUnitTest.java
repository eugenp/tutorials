package com.baeldung.randomhexnumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RandomHexGeneratorUnitTest {

    private static RandomHexGenerator randomHexGenerator;

    @BeforeAll
    static void setUp() {
        randomHexGenerator = new RandomHexGenerator();
    }

    @Test
    void whenGeneratingUnboundedHex_thenWillGetHexValue() {
        String randomHex = randomHexGenerator.generateUnboundedRandomHexUsingRandomNextInt();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingBoundedHex_thenWillGetHexWithinRange() {
        String randomHex = randomHexGenerator.generateRandomHexUsingRandomNextIntWithInRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingLongHex_thenWillGetHex() {
        String randomHex = randomHexGenerator.generateUnboundedRandomHexUsingRandomNextLong();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingSecureUnboundedHex_thenWillGetHexValue() {
        String randomHex = randomHexGenerator.generateRandomHexUsingSecureRandomNextInt();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingSecureBoundedHex_thenWillGetHexWithinRange() {
        String randomHex = randomHexGenerator.generateRandomHexUsingSecureRandomNextIntWithInRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingSecureLongHex_thenWillGetHex() {
        String randomHex = randomHexGenerator.generateRandomHexUsingSecureRandomNextLong();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingUsingStringFormatter_thenWillGetHex() {
        String randomHex = randomHexGenerator.generateRandomHexWithStringFormatter();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingUsingCommonsMathForGivenLength_thenWillGetHexWithGivenLength() {
        String randomHex = randomHexGenerator.generateRandomHexWithCommonsMathRandomDataGenerator(10);

        assertNotNull(randomHex);
        assertEquals(10, randomHex.length());
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingSecureRandomHexStringUsingCommonsMath_thenWillGetHexWithGivenLength() {
        String randomHex = randomHexGenerator.generateSecureRandomHexWithCommonsMathRandomDataGenerator(10);

        assertNotNull(randomHex);
        assertEquals(10, randomHex.length());
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingBoundedHexUsingCommonsMath_thenWillGetHexInRange() {
        String randomHex = randomHexGenerator.generateRandomHexWithCommonsMathRandomDataGeneratorNextIntWithRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void whenGeneratingSecureBoundedHexUsingCommonsMath_thenWillGetHexInRange() {
        String randomHex = randomHexGenerator.generateRandomHexWithCommonsMathRandomDataGeneratorSecureNextIntWithRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

}