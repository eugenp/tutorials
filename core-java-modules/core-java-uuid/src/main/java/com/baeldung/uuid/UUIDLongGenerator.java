package com.baeldung.uuid;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDLongGenerator {
    public long getLeastSignificantBits(){
        return UUID.randomUUID().getLeastSignificantBits();
    }

    public long getMostSignificantBits(){
        return UUID.randomUUID().getMostSignificantBits();
    }

    public long gethashCode(){
        return UUID.randomUUID().toString().hashCode();
    }

    public long combineByteBuffer(){
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        bb.rewind(); // Kembalikan posisi buffer ke awal
        return bb.getLong();
    }

    public long combineBitwise(){
        UUID uniqueUUID;
        uniqueUUID = UUID.randomUUID();
        return (uniqueUUID.getMostSignificantBits() << 32) | (uniqueUUID.getLeastSignificantBits() & 0xFFFFFFFFL);
    }

    public long combineDirect(){
        UUID uniqueUUID = UUID.randomUUID();
        long mostSignificantBits = uniqueUUID.getMostSignificantBits();
        long leastSignificantBits = uniqueUUID.getLeastSignificantBits();
        return mostSignificantBits ^ (leastSignificantBits >> 1);
    }

    public long combinePermutation(){
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
        return result;
    }
}
