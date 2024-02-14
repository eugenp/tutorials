package com.baeldung.uuid;

import com.fasterxml.uuid.impl.UUIDUtil;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.apache.commons.io.IOUtils.byteArray;
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

    private byte[] toByteArrayBitwise(UUID uuid) {
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

//    private byte[] toByteArray(UUID uuid) {
//        long msb = uuid.getMostSignificantBits();
//        long lsb = uuid.getLeastSignificantBits();
//
//        String binaryString = Long.toBinaryString(msb) + Long.toBinaryString(lsb);
//
//        // Memastikan panjang string biner menggunakan StringBuilder
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 128 - binaryString.length(); i++) {
//            sb.append('0');
//        }
//        sb.append(binaryString);
//        String paddedString = sb.toString();
//
//        // Mengubah string biner menjadi array byte
//        byte[] bytes = new byte[paddedString.length() / 8];
//        for (int i = 0; i < bytes.length; i++) {
//            String byteString = paddedString.substring(i * 8, (i + 1) * 8);
//            bytes[i] = (byte) Integer.parseInt(byteString, 2);
//        }
//
//        return bytes;
//    }

    private byte[] toByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        // Menggabungkan most significant bits dan least significant bits menjadi satu nilai long
        byte[] uuidBytes = new byte[16];
        ByteBuffer.wrap(uuidBytes)
                .putLong(msb)
                .putLong(lsb);

        return uuidBytes;
    }

    @Test
    public void whenGivenUUID_thenVerified() {
        for (int i = 0; i < 100; i++) {
            UUID uuid = UUID.randomUUID();
            byte[] bytes = toByteArray(uuid);
//            byte[] bytes = UUIDUtil.asByteArray(uuid);

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
