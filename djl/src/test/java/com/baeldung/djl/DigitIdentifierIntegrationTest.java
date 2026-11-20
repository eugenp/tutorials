package com.baeldung.djl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DigitIdentifierIntegrationTest {

    @ParameterizedTest
    @ValueSource(strings = { "data/3_991.png", "data/3_1028.png", "data/3_9882.png", "data/3_9996.png" })
    void whenRunModel_thenIdentifyDigitCorrectly(String imagePath) throws Exception {
        DigitIdentifier digitIdentifier = new DigitIdentifier();
        String identifiedDigit = digitIdentifier.identifyDigit(imagePath);
        assertEquals("3", identifiedDigit);
    }
}
