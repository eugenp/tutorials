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

    private byte[] toByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];
        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }
        return buffer;
    }

    @Test
    public void whenGivenUUID_thenVerified() {
        for (int i = 0; i < 100; i++) {
            UUID uuid = UUID.randomUUID();
            byte[] bytes = toByteArray(uuid);

            // assert that byte at index 6 is 0x40 (version 4)
            byte byte6 = bytes[6];
            assertThat(byte6 & 0xF0).isEqualTo(0x40);

            // assert that the byte at index 8 is 0x80 (IETF type variant)
            byte byte8 = bytes[8];
            assertThat(byte8 & 0xC0).isEqualTo(0x80);

            // 1 byte = 8 bites
            int totalBites = bytes.length * 8;

            // totalBites - 6 (4 bits for version and 2 bits for variant).
            int randomBitsCount = totalBites - 6;

            // assert that number of random bits is 122
            assertThat(randomBitsCount).isEqualTo(122);
        }
    }

}
