package com.baeldung.endianness;

import java.nio.ByteBuffer;

public class Endianness {

    public static void main(String[] args) {
        int value = 123456789;
        byte[] bytes = ByteBuffer.allocate(4)
          .putInt(value)
          .array();

        for (byte b : bytes) {
            System.out.format("0x%x ", b);
        }
    }
}
