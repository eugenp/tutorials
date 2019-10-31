package com.baeldung.jni;

public class HelloWorldJNI {

    static {
        System.loadLibrary("native");
    }
    
    public static void main(String[] args) {
        new HelloWorldJNI().sayHello();
    }

    // Declare a native method sayHello() that receives no arguments and returns void
    public native String sayHello();
}
