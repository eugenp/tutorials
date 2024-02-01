package com.baeldung.uuid;

import static org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.lang3.Conversion;
import org.junit.jupiter.api.Test;

public class EncodeUUIDToBase64StringUnitTest {
    private final UUID originalUUID = UUID.fromString("cc5f93f7-8cf1-4a51-83c6-e740313a0c6c");

    @Test
    public void givenUUID_whenEncodingUsingBase64Encoder_thenGiveExpectedEncodedString() {
        String expectedEncodedString = "UUrxjPeTX8xsDDoxQOfGgw";
        byte[] uuidBytes = convertToByteArray(originalUUID);
        String encodedUUID = Base64.getEncoder().withoutPadding()
          .encodeToString(uuidBytes);
        assertEquals(expectedEncodedString, encodedUUID);
    }

    @Test
    public void givenUUID_whenEncodingUsingByteBufferAndBase64UrlEncoder_thenGiveExpectedEncodedString() {
        String expectedEncodedString = "zF-T94zxSlGDxudAMToMbA";
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(originalUUID.getMostSignificantBits());
        byteBuffer.putLong(originalUUID.getLeastSignificantBits());
        String encodedUUID = Base64.getUrlEncoder().withoutPadding()
          .encodeToString(byteBuffer.array());
        assertEquals(expectedEncodedString, encodedUUID);
    }

    @Test
    public void givenUUID_whenEncodingUsingApacheUtils_thenGiveExpectedEncodedString() {
        String expectedEncodedString = "UUrxjPeTX8xsDDoxQOfGgw";
        byte[] bytes = Conversion.uuidToByteArray(originalUUID, new byte[16], 0, 16);
        String encodedUUID = encodeBase64URLSafeString(bytes);
        assertEquals(expectedEncodedString, encodedUUID);
    }

    private byte[] convertToByteArray(UUID uuid) {
        byte[] result = new byte[16];

        long mostSignificantBits = uuid.getMostSignificantBits();
        fillByteArray(0, 8, result, mostSignificantBits);

        long leastSignificantBits = uuid.getLeastSignificantBits();
        fillByteArray(8, 16, result, leastSignificantBits);

        return result;
    }

    private static void fillByteArray(int start, int end, byte[] result, long bits) {
        for (int i = start; i < end; i++) {
            int shift = i * 8;
            result[i] = (byte) ((int) (255L & bits >> shift));
        }
    }
}
