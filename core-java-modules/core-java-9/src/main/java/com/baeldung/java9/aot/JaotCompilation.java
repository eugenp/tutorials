package com.baeldung.java9.aot;

public class JaotCompilation {

    public static void main(String[] argv) {
        System.out.println(message());
    }

    public static String message() {
        return "The JAOT compiler says 'Hello'";
    }
}