package com.baeldung.uuid;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {

    @Test
    public void whengetMostSignificantBits_thenAssertPositive() {
        long randomPositiveLong = Math.abs(UUID.randomUUID().getMostSignificantBits());
        assertThat(randomPositiveLong).isPositive();
    }

    @Test
    public void whengetLeastSignificantBits_thenAssertPositive() {
        long randomPositiveLong = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        assertThat(randomPositiveLong).isPositive();
    }

}
