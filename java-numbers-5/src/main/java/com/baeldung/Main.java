package com.baeldung;

public class Main {

    public static void main(String[] args) {
        int x = 0xff;
        System.out.println(x); // output is 255

        byte y = (byte) 0xff;
        System.out.println(y); // output is -1

        int rgba = 272214023;
        int r = rgba >> 24;
        int g = rgba >> 16 & 0xFF;
        int b = rgba >> 8 & 0xFF;
        int a = rgba & 0xFF;

        System.out.println(r); // output is 64
        System.out.println(g); // output is 57
        System.out.println(b); // output is 168
        System.out.println(a); // output is 7
    }
}
