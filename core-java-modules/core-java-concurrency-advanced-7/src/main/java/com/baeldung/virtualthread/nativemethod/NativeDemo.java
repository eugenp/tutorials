package com.baeldung.virtualthread.nativemethod;

public class NativeDemo {

    static {
        System.loadLibrary("native-lib");
    }

    public native String nativeCall();
}
