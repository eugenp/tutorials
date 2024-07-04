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
    void givenGeneratingUnboundedHex_thenWillGetHexValue() {
        String randomHex = randomHexGenerator.generateUnboundedRandomHexUsingRandomNextInt();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingBoundedHex_thenWillGetHexWithinRange() {
        String randomHex = randomHexGenerator.generateRandomHexUsingRandomNextIntWithInRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingLongHex_thenWillGetHex() {
        String randomHex = randomHexGenerator.generateUnboundedRandomHexUsingRandomNextLong();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingSecureUnboundedHex_thenWillGetHexValue() {
        String randomHex = randomHexGenerator.generateRandomHexUsingSecureRandomNextInt();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingSecureBoundedHex_thenWillGetHexWithinRange() {
        String randomHex = randomHexGenerator.generateRandomHexUsingSecureRandomNextIntWithInRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingSecureLongHex_thenWillGetHex() {
        String randomHex = randomHexGenerator.generateRandomHexUsingSecureRandomNextLong();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingUsingStringFormatter_thenWillGetHex() {
        String randomHex = randomHexGenerator.generateRandomHexWithStringFormatter();

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingUsingCommonsMathForGivenLength_thenWillGetHexWithGivenLength() {
        String randomHex = randomHexGenerator.generateRandomHexWithCommonsMathRandomDataGenerator(10);

        assertNotNull(randomHex);
        assertEquals(10, randomHex.length());
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingSecureRandomHexStringUsingCommonsMath_thenWillGetHexWithGivenLength() {
        String randomHex = randomHexGenerator.generateSecureRandomHexWithCommonsMathRandomDataGenerator(10);

        assertNotNull(randomHex);
        assertEquals(10, randomHex.length());
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingBoundedHexUsingCommonsMath_thenWillGetHexInRange() {
        String randomHex = randomHexGenerator.generateRandomHexWithCommonsMathRandomDataGeneratorNextIntWithRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

    @Test
    void givenGeneratingSecureBoundedHexUsingCommonsMath_thenWillGetHexInRange() {
        String randomHex = randomHexGenerator.generateRandomHexWithCommonsMathRandomDataGeneratorSecureNextIntWithRange(500, 1000);

        assertNotNull(randomHex);
        assertFalse(randomHex.isEmpty());
        assertTrue(randomHex.matches("[0-9a-f]+"));
    }

}