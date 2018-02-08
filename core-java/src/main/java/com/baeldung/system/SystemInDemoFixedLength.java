package com.baeldung.system;

import java.io.IOException;

public class SystemInDemoFixedLength {
    public static void main(String[] args) {
        final int length = 10;
        byte[] name = new byte[length];

        System.out.print("Please enter your name (upto " + length + " characters): ");
        try {
            System.in.read(name, 0, length);

            System.out.println("Hello " + new String(name));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
