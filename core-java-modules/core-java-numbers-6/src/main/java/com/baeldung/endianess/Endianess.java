package com.baeldung.endianess;

import java.nio.ByteBuffer;

public class Endianess {

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
