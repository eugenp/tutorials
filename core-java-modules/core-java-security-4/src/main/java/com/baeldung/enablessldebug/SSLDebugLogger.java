package com.baeldung.enablessldebug;

public class SSLDebugLogger {
    public static void enableSSLDebugUsingSystemProperties() {
        System.setProperty("javax.net.debug", "ssl:debug");
    }
}
