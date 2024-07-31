package com.baeldung.uuid;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class UuidHelper {

    public static byte[] convertUUIDToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println("Original UUID: " + uuid);

        byte[] bytes = convertUUIDToBytes(uuid);
        System.out.println("Converted byte array: " + Arrays.toString(bytes));

        UUID uuidNew = convertBytesToUUID(bytes);
        System.out.println("Converted UUID: " + uuidNew);
    }
}
