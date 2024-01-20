package com.baeldung.securerandompositivelong;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.assertThat;

public class SecureRandomPositiveLongUnitTest {

    @Test
    void whenGenerateRecureRandom_thenGetExpectedValue() {
        SecureRandom secureRandom = new SecureRandom();
        long randomPositiveLong = Math.abs(secureRandom.nextLong());

        assertThat(randomPositiveLong).isPositive();

        Double pc = 1.0 / Math.pow(2, 63);
        System.out.printf("%.40f", pc);

        assertThat(pc).isLessThan(0.00000000000000001);
    }
}
