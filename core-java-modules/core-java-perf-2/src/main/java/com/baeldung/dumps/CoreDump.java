package com.baeldung.dumps;

public class CoreDump {
    static {
        System.loadLibrary("nativelib");
    }

    public static void main(String[] args) {
        new CoreDump().core();
    }

    private native void core();
}
