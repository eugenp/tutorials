package com.baeldung.jni;

public class RegisterNativesHelloWorldJNI {

    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        RegisterNativesHelloWorldJNI helloWorldJNI = new RegisterNativesHelloWorldJNI();
        helloWorldJNI.register();
        helloWorldJNI.sayHello();
    }

    public native String sayHello();

    public native void register();
}
