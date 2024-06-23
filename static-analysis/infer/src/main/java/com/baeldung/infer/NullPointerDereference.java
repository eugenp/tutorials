package com.baeldung.infer;

public class NullPointerDereference {

    public static void main(String[] args) {
        NullPointerDereference.nullPointerDereference();
    }

    private static void nullPointerDereference() {
        String str = null;
        int length = str.length();
    }
}
