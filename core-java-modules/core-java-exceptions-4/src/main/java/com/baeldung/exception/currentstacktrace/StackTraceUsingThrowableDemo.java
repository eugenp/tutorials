package com.baeldung.exception.currentstacktrace;

public class StackTraceUsingThrowableDemo {

    public static void main(String[] args) {
        methodA();
    }

    public static StackTraceElement[] methodA() {
        try {
            methodB();
        } catch (Throwable t) {
            return t.getStackTrace();
        }
        return null;
    }

    public static void methodB() throws Throwable {
        throw new Throwable("A test exception");
    }
}
