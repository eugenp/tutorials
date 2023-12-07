package com.baeldung.unsatisfiedlink;

public class JniUnsatisfiedLink {

    public static final String LIB_NAME = "test";

    public static void main(String[] args) {
        System.loadLibrary(LIB_NAME);
        new JniUnsatisfiedLink().test();
    }

    public native String test();

    public native String nonexistentDllMethod();
}
