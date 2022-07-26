package com.baeldung.exception.currentstacktrace;

package main.java.com.baeldung.tutorials;

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
