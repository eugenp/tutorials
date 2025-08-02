package com.baeldung.uuid;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {

    @Test
    public void whenGetMostSignificantBits_thenAssertPositive() {
        long randomPositiveLong = Math.abs(UUID.randomUUID().getMostSignificantBits());
        assertThat(randomPositiveLong).isNotNegative();
    }

    @Test
    public void whenGetLeastSignificantBits_thenAssertPositive() {
        long randomPositiveLong = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        assertThat(randomPositiveLong).isNotNegative();
    }
}
