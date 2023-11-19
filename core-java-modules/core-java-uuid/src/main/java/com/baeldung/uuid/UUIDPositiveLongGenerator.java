package com.baeldung.uuid;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Methods are called by reflection in the unit test
 */
@SuppressWarnings("unused")
public class UUIDPositiveLongGenerator {
    public long getLeastSignificantBits() {
        return Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }

    public long getMostSignificantBits() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    public long combineByteBuffer() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        bb.rewind();
        return Math.abs(bb.getLong());
    }

    public long combineBitwise() {
        UUID uniqueUUID = UUID.randomUUID();
        long mostSignificantBits = uniqueUUID.getMostSignificantBits();
        long leastSignificantBits = uniqueUUID.getLeastSignificantBits();
        return Math.abs((mostSignificantBits << 32) | (leastSignificantBits & 0xFFFFFFFFL));
    }

    public long combineDirect() {
        UUID uniqueUUID = UUID.randomUUID();
        long mostSignificantBits = uniqueUUID.getMostSignificantBits();
        long leastSignificantBits = uniqueUUID.getLeastSignificantBits();
        return Math.abs(mostSignificantBits ^ (leastSignificantBits >> 1));
    }

    public long combinePermutation() {
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        byte[] uuidBytes = new byte[16];

        for (int i = 0; i < 8; i++) {
            uuidBytes[i] = (byte) (mostSigBits >>> (8 * (7 - i)));
            uuidBytes[i + 8] = (byte) (leastSigBits >>> (8 * (7 - i)));
        }

        long result = 0;
        for (byte b : uuidBytes) {
            result = (result << 8) | (b & 0xFF);
        }
        return Math.abs(result);
    }

    public long combineWithSecureRandom() {
        UUID uniqueUUID = UUID.randomUUID();
        SecureRandom secureRandom = new SecureRandom();
        long randomBits = secureRandom.nextLong();

        long mostSignificantBits = uniqueUUID.getMostSignificantBits() ^ randomBits;
        long leastSignificantBits = uniqueUUID.getLeastSignificantBits();

        return Math.abs((mostSignificantBits << 32) | (leastSignificantBits & 0xFFFFFFFFL));
    }

    public long combineWithNanoTime() {
        UUID uniqueUUID = UUID.randomUUID();
        long nanoTime = System.nanoTime();

        long mostSignificantBits = uniqueUUID.getMostSignificantBits() ^ nanoTime;
        long leastSignificantBits = uniqueUUID.getLeastSignificantBits();

        return Math.abs((mostSignificantBits << 32) | (leastSignificantBits & 0xFFFFFFFFL));
    }


}
