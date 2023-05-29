package com.baeldung.arrayconcat;

import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

public class CombiningByteArrays {

    public byte[] combineArraysUsingArrayCopy(byte[] first, byte[] second) {
        byte[] combined = new byte[first.length + second.length];

        System.arraycopy(first, 0, combined, 0, first.length);
        System.arraycopy(second, 0, combined, first.length, second.length);
        return combined;
    }

    public byte[] combineArraysUsingByteBuffer(byte[] first, byte[] second, byte[] third) {
        byte[] combined = new byte[first.length + second.length + third.length];

        ByteBuffer buffer = ByteBuffer.wrap(combined);
        buffer.put(first);
        buffer.put(second);
        buffer.put(third);
        return buffer.array();
    }

    public byte[] combineArraysUsingCustomMethod(byte[] first, byte[] second) {
        byte[] combined = new byte[first.length + second.length];

        for (int i = 0; i < combined.length; ++i) {
            combined[i] = i < first.length ? first[i] : second[i - first.length];
        }
        return combined;
    }

    public byte[] combineArraysUsingGuava(byte[] first, byte[] second, byte[] third) {
        byte[] combined = Bytes.concat(first, second, third);

        return combined;
    }

    public byte[] combineArraysUsingApacheCommons(byte[] first, byte[] second) {
        byte[] combined = ArrayUtils.addAll(first, second);

        return combined;
    }

}
