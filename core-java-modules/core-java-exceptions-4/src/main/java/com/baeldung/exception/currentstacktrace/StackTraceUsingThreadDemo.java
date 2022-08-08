package com.baeldung.exception.currentstacktrace;

public class StackTraceUsingThreadDemo {

    public static void main(String[] args) {
        methodA();
    }

    public static StackTraceElement[] methodA() {
        return methodB();
    }

    public static StackTraceElement[] methodB() {
        return Thread.currentThread().getStackTrace();
    }
}
