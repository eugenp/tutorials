package com.baeldung.exception.currentstacktrace;

public class DumpStackTraceDemo
{
    public static void main(String[] args) {
        methodA();
    }

    public static void methodA() {
        methodB();
    }

    public static void methodB() {
        Thread.dumpStack();
        Thread.currentThread().getStackTrace();
    }
}
