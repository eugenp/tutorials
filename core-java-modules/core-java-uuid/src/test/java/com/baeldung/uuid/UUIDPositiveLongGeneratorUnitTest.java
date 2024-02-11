package com.baeldung.uuid;

import com.fasterxml.uuid.impl.UUIDUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDPositiveLongGeneratorUnitTest {

    private void verifyUUID(UUID uuid) {
        byte[] bytes = toByteArray(uuid);

        // assert that byte at index 6 is 0x40 (version 4)
        byte byte6 = bytes[6];
        assertThat(byte6 & 0xF0).isEqualTo(0x40);

        // assert that the byte at index 8 is 0x80 (IETF type variant)
        byte byte8 = bytes[8];
        assertThat(byte8 & 0xC0).isEqualTo(0x80);
    }

    private boolean[] getFirst122Bits(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        boolean[] bits = new boolean[122]; // Untuk menyimpan 122 bit pertama dari UUID

        // Konversi 64 bit pertama (Most Significant Bits) menjadi bit
        for (int i = 0; i < 64; i++) {
            bits[i] = ((msb >> (63 - i)) & 1) == 1; // Mendapatkan nilai bit ke-i
        }
        return bits;
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
            verifyUUID(uuid);

            long msbfirst = uuid.getMostSignificantBits() >>> 6;
            System.out.println(msbfirst);
        }
    }

}
